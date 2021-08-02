
package com.reactlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.renderscript.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RNImageFilterAndroidModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  HashMap<String,Bitmap> BitMapHashMap=new HashMap<>();

  public RNImageFilterAndroidModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @NonNull
  @Override
  public String getName() {
    return "FilterImage";
  }


  @ReactMethod
  public void MirrorImage(String ImageUri,Integer type,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Matrix matrix = new Matrix();
      if(type==1) {
        matrix.postScale(-1, 1);
      }
      else if(type==2)
      {
        matrix.postScale(1,-1);
      }
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap, 0, 0, ImageBitMap.getWidth(), ImageBitMap.getHeight(), matrix, true);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    }
  }

  @ReactMethod
  public void RotateImage(String ImageUri,Integer Rotation, final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Matrix matrix = new Matrix();
      matrix.postRotate(Rotation);
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap, 0, 0, ImageBitMap.getWidth(), ImageBitMap.getHeight(), matrix, true);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    }
  }

  @ReactMethod
  public void SharpenImage(String ImageUri,Integer Sharpen,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      float[] sharp = { 0, -Sharpen, 0, -Sharpen, 5f*Sharpen, -Sharpen, 0, -Sharpen, 0};
      Bitmap BitmapOperation = Bitmap.createBitmap(
              ImageBitMap.getWidth(), ImageBitMap.getHeight(),
              Bitmap.Config.ARGB_8888);

      RenderScript rs = RenderScript.create(this.getReactApplicationContext());

      Allocation allocIn = Allocation.createFromBitmap(rs, ImageBitMap);
      Allocation allocOut = Allocation.createFromBitmap(rs, BitmapOperation);

      ScriptIntrinsicConvolve3x3 convolution = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
      convolution.setInput(allocIn);
      convolution.setCoefficients(sharp);
      convolution.forEach(allocOut);

      allocOut.copyTo(BitmapOperation);

      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);

    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void ScaleDownImage(String ImageUri,Integer Scale,final Promise promise)
  {
    if(Scale<1)
    {
      promise.reject("Error","Please enter a value that is greater than or equal to 1");
      return;
    }
    Uri UriOfImage=Uri.parse(ImageUri);
    URL ImageUrl= null;
    try {
      ImageUrl = new URL(UriOfImage.toString());
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inSampleSize = (int)Math.pow(2,Scale);
      Bitmap bitmap= BitmapFactory.decodeStream(ImageUrl.openConnection().getInputStream(),null,options);
      if(bitmap!=null) {
        Bitmap RotatedBitMap=RotateBitMap(ImageUrl,bitmap,promise);
        if(RotatedBitMap!=null) {
          String ImagePath="";
          ImagePath = CreateImageFile(RotatedBitMap, promise);
          BitMapHashMap.put(ImagePath,RotatedBitMap);
          promise.resolve(ImagePath);
        }
        else
        {
          String ImagePath="";
          ImagePath = CreateImageFile(bitmap, promise);
          BitMapHashMap.put(ImagePath,bitmap);
          promise.resolve(ImagePath);
        }
      }
      else {
        promise.reject("Error","File Could Not Be Processed");
        return;
      }
    } catch (MalformedURLException e) {
      promise.reject("Error",e.getMessage());
      return;
    } catch (IOException e) {
      promise.reject("Error",e.getMessage());
      return;
    }
  }

  @ReactMethod
  public void SetSepiaFilter(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float[] src={0.393f, 0.769f, 0.189f, 0, 0,
              0.349f, 0.686f, 0.168f, 0, 0,
              0.272f, 0.543f, 0.131f, 0, 0,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetHue(String ImageUri,Integer Hue,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      if ((Hue < 0) || (Hue > 360)) { return; }

      int size = ImageBitMap.getWidth()*ImageBitMap.getHeight();
      int[] all_pixels = new int [size];
      int top = 0;
      int left = 0;
      int offset = 0;
      int stride = ImageBitMap.getWidth();

      ImageBitMap.getPixels (all_pixels, offset, stride, top, left, ImageBitMap.getWidth(), ImageBitMap.getHeight());

      int pixel = 0;
      int alpha = 0;
      float[] hsv = new float[3];

      for (int i=0; i < size; i++) {
        pixel = all_pixels [i];
        alpha = Color.alpha (pixel);
        Color.colorToHSV (pixel, hsv);

        // You could specify target color including Saturation for
        // more precise results
        hsv [0] = Hue;

        all_pixels [i] = Color.HSVToColor (alpha, hsv);
      }

      BitmapOperation.setPixels (all_pixels, offset, stride, top, left, ImageBitMap.getWidth(), ImageBitMap.getHeight());
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    }
    else {
      promise.reject("Error", "Bitmap could not be processed");
    }

  }

  @ReactMethod
  public void SetBlurFilter(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      RenderScript rs = RenderScript.create(this.getReactApplicationContext());

      //Create allocation from Bitmap
      Allocation allocation = Allocation.createFromBitmap(rs, ImageBitMap);

      Type t = allocation.getType();

      //Create allocation with the same type
      Allocation blurredAllocation = Allocation.createTyped(rs, t);

      //Create script
      ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
      //Set blur radius (maximum 25.0)
      blurScript.setRadius(25);
      //Set input for script
      blurScript.setInput(allocation);
      //Call script for output allocation
      blurScript.forEach(blurredAllocation);

      //Copy script result into bitmap

      blurredAllocation.copyTo(BitmapOperation);
      //Destroy everything to free memory
      blurScript.destroy();
      blurredAllocation.destroy();
      allocation.destroy();
      rs.destroy();
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }


  @ReactMethod
  public void SetContrast(String ImageUri,float Contrast,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float factor=(259*(Contrast+255))/(255*(259-Contrast));
      float[] src={factor, 0, 0, 0, (-128*factor)+128,
              0, factor, 0, 0, (-128*factor)+128,
              0, 0, factor, 0, (-128*factor)+128,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }

  }

  @ReactMethod
  public void SetBlueFilter(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float[] src={0, 0, 0, 0, 0,
              0, 0, 0, 0, 0,
              0, 0, 1, 0, 0,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetGreenFilter(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float[] src={0, 0, 0, 0, 0,
              0, 1, 0, 0, 0,
              0, 0, 0, 0, 0,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetRedFilter(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float[] src={1, 0, 0, 0, 0,
              0, 0, 0, 0, 0,
              0, 0, 0, 0, 0,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }
  @ReactMethod
  public void SetBrightness(String ImageUri,float Brightness, final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      colorMatrix.setScale(Brightness,Brightness,Brightness,Brightness);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetSaturation(String ImageUri,float Saturation, final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
    if (ImageBitMap != null) {
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      colorMatrix.setSaturation(Saturation);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetNegative(String ImageUri,final Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    if (ImageBitMap != null) {
      Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      float[] src={-1, 0, 0, 0, 255,
              0, -1, 0, 0, 255,
              0, 0, -1, 0, 255,
              0, 0, 0, 1, 0};
      colorMatrix.set(src);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }
  }

  @ReactMethod
  public void SetGrayScale(String ImageUri,final  Promise promise)
  {
    Bitmap ImageBitMap=null;
    if(BitMapHashMap.containsKey(ImageUri))
    {
      ImageBitMap=BitMapHashMap.get(ImageUri);
    }
    else {
      ImageBitMap = GetNewBitMap(ImageUri, promise);
      BitMapHashMap.put(ImageUri,ImageBitMap);
    }
    Bitmap BitmapOperation = Bitmap.createBitmap(ImageBitMap.getWidth(), ImageBitMap.getHeight(), ImageBitMap.getConfig());
    if (ImageBitMap != null) {
      Canvas canvas=new Canvas(BitmapOperation);
      Paint paint=new Paint();
      ColorMatrix colorMatrix=new ColorMatrix();
      colorMatrix.setSaturation(0);
      ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
      paint.setColorFilter(colorMatrixColorFilter);
      canvas.drawBitmap(ImageBitMap,0,0,paint);
      String ImagePath="";
      ImagePath = CreateImageFile(BitmapOperation, promise);
      BitMapHashMap.put(ImagePath,BitmapOperation);
      promise.resolve(ImagePath);
    } else {
      promise.reject("Error", "Bitmap could not be processed");
    }

  }

  public Bitmap GetNewBitMap(String ImageUri,Promise promise)
  {
    Uri UriOfImage=Uri.parse(ImageUri);
    URL ImageUrl= null;
    try {
      ImageUrl = new URL(UriOfImage.toString());
      Bitmap bitmap= BitmapFactory.decodeStream(ImageUrl.openConnection().getInputStream());
      if(bitmap!=null) {
        Bitmap RotatedBitMap=RotateBitMap(ImageUrl,bitmap,promise);
        if(RotatedBitMap!=null) {
          return RotatedBitMap;
        }
        else
        {
          return bitmap;
        }
      }
      else {
        promise.reject("Error","File Could Not Be Processed");
        return null;
      }
    } catch (MalformedURLException e) {
      promise.reject("Error",e.getMessage());
      return null;
    } catch (IOException e) {
      promise.reject("Error",e.getMessage());
      return null;
    }
  }


  public String CreateImageFile(Bitmap ImageBitMap,Promise promise)
  {
    File ImageFile = this.getReactApplicationContext().getCacheDir();
    try {
      ImageFile = createTempFile(UUID.randomUUID().toString(), ".png", ImageFile);
      FileOutputStream fOut = new FileOutputStream(ImageFile);
      if(ImageBitMap!=null) {
        ImageBitMap.compress(Bitmap.CompressFormat.PNG, 10, fOut);
      }
      fOut.flush();
      fOut.close();
      return "file://" + ImageFile.getAbsolutePath();
    } catch (IOException e) {
      promise.reject("Error",e.getMessage());
      return null;
    }
  }




  public Bitmap RotateBitMap(URL ImageUrl,Bitmap bitmap,Promise promise)
  {
    ExifInterface exifInterface = null;
    try {
      exifInterface = new ExifInterface(ImageUrl.getPath());
      int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
      Matrix matrix = new Matrix();
      int rotation = 0;
      if (orientation == 6)
        rotation = 90;
      else if (orientation == 3)
        rotation = 180;
      else if (orientation == 8)
        rotation = 270;
      if (rotation != 0 && bitmap!=null) {
        matrix.postRotate(rotation);
        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        bitmap = rotated;

      }
      return bitmap;
    } catch (IOException e) {
      promise.reject("Error",e.getMessage());
      return null;
    }
  }


}