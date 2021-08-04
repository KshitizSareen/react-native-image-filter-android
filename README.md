
# react-native-image-filter-android

## Getting started

`$ npm install react-native-image-filter-android --save`

### Mostly automatic installation

`$ react-native link react-native-image-filter-android`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-image-filter-android` and add `RNImageFilterAndroid.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNImageFilterAndroid.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNImageFilterAndroidPackage;` to the imports at the top of the file
  - Add `new RNImageFilterAndroidPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-image-filter-android'
  	project(':react-native-image-filter-android').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-image-filter-android/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-image-filter-android')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNImageFilterAndroid.sln` in `node_modules/react-native-image-filter-android/windows/RNImageFilterAndroid.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Image.Filter.Android.RNImageFilterAndroid;` to the usings at the top of the file
  - Add `new RNImageFilterAndroidPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage

#### Import Library
```javascript

import FilterImage from 'react-native-image-filter-android';

```

#### Scale Down Image
 
 ```javascript
    const res= await FilterImage.ScaleDownImage(Image,Scale); 
	//Image: Input URI of image (String)
	//Scale: The Factor by which you want to reduce the width and height of the image (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
   ```

#### Set Contrast Of Image

```javascript
     const res=await FilterImage.SetContrast(Image,Contrast);
	//Image: Input URI of image (String)
	//Contrast: The Contrast that you want to apply the Image (Number)
     console.log(res);
	 //res: The Output URI of the Image (String)
```
#### Set Hue Of Image

```javascript
     const res=await FilterImage.SetHue(Image,Hue);
	//Image: Input URI of image (String)
	//Hue: The Hue that you want to apply the Image (Number)
     console.log(res);
	 //res: The Output URI of the Image (String)
```

#### Set Saturation Of Image

```javascript
    const res=await FilterImage.SetSaturation(Image,Saturation);
	//Image: Input URI of image (String)
	//Saturation: The Saturation that you want to apply the Image (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Brightness Of Image

  ```javascript
    const res=await FilterImage.SetBrightness(Image,Brightness);
	//Image: Input URI of image (String)
	//Brightness: The Brightness that you want to apply the Image (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```
#### Sharpen Image

  ```javascript
    const res=await FilterImage.SharpenImage(Image,Sharpen);
	//Image: Input URI of image (String)
	//Sharpen: The amount by which you want to sharpen the image (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
```
#### Set Sepia Filter

  ```javascript
    const res=await FilterImage.SetSepiaFilter(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```
#### Mirror Image

  ```javascript
    const res=await FilterImage.MirrorImage(Image,Type);
	//Image: Input URI of image (String)
	//Type: If Type is 1, the image will be mirrored horizontally (Number)
	//Type: If Type is 2, the image will be mirrored vertically (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
```

#### Rotate Image by any angle

```javascript
    const res=await FilterImage.RotateImage(Image,Rotation);
	//Image: Input URI of image (String)
	//Rotation: The amount by which you want to rotate the image (Number)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Blue Filter Of Image

  ```javascript
    const res=await FilterImage.SetBlueFilter(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Blur Filter Of Image

  ```javascript
    const res=await FilterImage.SetBlurFilter(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```
#### Set GrayScale Of Image

```javascript
    const res=await FilterImage.SetGrayScale(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Green Filter Of Image

  ```javascript
    const res=await FilterImage.SetGreenFilter(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Negative Filter Of Image

  ```javascript
    const res=await FilterImage.SetNegative(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```

#### Set Red Filter Of Image

  ```javascript
    const res=await FilterImage.SetRedFilter(Image);
	//Image: Input URI of image (String)
    console.log(res);
	//res: The Output URI of the Image (String)
  ```
  
  ## Methods

  | Method  | Return Type | Description                                                      |  
| ------  | ----------  |  --------------------------------------------------------------- |
| ScaleDownImage   | String | This method is used to reduce the width and height of the image, by passing a scale. The scale is an integer value. The new width and height are calculated as width'=width / 2^scale, height' = height / 2^scale. Use this method to reduce width and height of an image for faster processing |
| SetContrast | String | This method is used to apply contrast to an image |
| SetHue | String | This method is used to apply hue to an image |
| SetSaturation | String | This method is used to apply saturation to an image |
| SetBrightness | String | This method is used to apply brightness to an image. Any value less than or equal to 0 will return the original image. If the brightness is between 0 and 1, it will darken the image |
| SharpenImage | String | This method is used to sharpen an image.  Any value less than or equal to 0 will return the original image |
| SetSepiaFilter | String | This method is used to apply sepia filter to an image |
| MirrorImage | String | This method is used to mirror an image horizontally or vertically |
| RotateImage | String | This method is used to rotate an image with a specified angle |
| SetBlueFilter | String | This method is used to apply blue filter to an image|
| SetBlurFilter | String | This method is used to apply blur filter to an image|
| SetGrayScale | String | This method is used to apply grayscale filter to an image|
| SetGreenFilter | String | This method is used to apply green filter to an image|
| SetNegative | String | This method is used to apply negative filter to an image|
| SetRedFilter | String | This method is used to apply red filter to an image|

## Demo

![Alt Text](Demo.gif)