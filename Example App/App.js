/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

 import React,{Component} from 'react';
 import {
   Dimensions,
   TouchableOpacity,
   NativeModules,
   Image,
   View,
   Text,
   FlatList,
   ImageBackground
 } from 'react-native';
 import ImagePicker from 'react-native-image-crop-picker';
import FilterImage from 'react-native-image-filter-android';
import Slider from '@react-native-community/slider';
 
 const windowWidth=Dimensions.get('window').width;
 const windowHeight=Dimensions.get('window').height;
 
 class App extends Component
 {
   constructor(){
     super();
     this.state={
       uri:"",
       originaluri: "",
       Contrast: 0,
       Saturation: 1,
       Brighntess: 1,
       Sharpen: 0,
       ColorImages:[],
     }
    this.Contrast=0;
    this.Saturation=1;
    this.Brighntess=1;
    this.Sharpen=0;
   }

   ScaleDownImage= async (image)=>{
    const res= await FilterImage.ScaleDownImage(image,1);
    console.log(res);
    this.setState({uri:res});
    this.setState({originaluri: res});
    return res;
   }

   SetContrast= async (Image,Contrast)=>{
     const res=await FilterImage.SetContrast(Image,Contrast);
     console.log(res);
     return res;
   }

   SetHue=async (Image,Hue)=>{
     const res=await FilterImage.SetHue(Image,Hue);
     console.log(res);
     var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
   }

  SetSaturation=async (Image,Saturation)=>{
    const res=await FilterImage.SetSaturation(Image,Saturation);
    console.log(res);
    return res;
  }

  SetBrighntess=async (Image,Brighntess)=>{
    const res=await FilterImage.SetBrightness(Image,Brighntess);
    console.log(res);
    return res;
  }

  SharpenImage=async (Image,Sharpen)=>{
    const res=await FilterImage.SharpenImage(Image,Sharpen);
    console.log(res);
    return res;
  }
   
  FilterImage= async ()=>
  {
    const ContrastImage= await this.SetContrast(this.state.originaluri,this.Contrast);
    const SaturationImage=await this.SetSaturation(ContrastImage,this.Saturation);
    const BrightnessImage=await this.SetBrighntess(SaturationImage,this.Brighntess);
    const SharpenImage=await this.SharpenImage(BrightnessImage,this.Sharpen);
    this.setState({uri:SharpenImage});

  }

  SetSepia=async (Image)=>{
    const res=await FilterImage.SetSepiaFilter(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetMirror=async (Image,type)=>{
    const res=await FilterImage.MirrorImage(Image,type);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetRotation=async (Image,Rotation)=>{
    const res=await FilterImage.RotateImage(Image,Rotation);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetBlue=async (Image)=>{
    const res=await FilterImage.SetBlueFilter(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetBlur=async (Image)=>{
    const res=await FilterImage.SetBlurFilter(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetGrayScale=async (Image)=>{
    const res=await FilterImage.SetGrayScale(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetGreenScale=async (Image)=>{
    const res=await FilterImage.SetGreenFilter(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetNegative=async (Image)=>{
    const res=await FilterImage.SetNegative(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

  SetRed=async (Image)=>{
    const res=await FilterImage.SetRedFilter(Image);
    console.log(res);
    var ColorImages=this.state.ColorImages;
     ColorImages.push(res);
     this.setState({ColorImages: ColorImages});
  }

   componentDidMount(){
   }
   render(){ 
     
    return (
      <View style={{
        flex: 1,
        alignItems: 'center',
        backgroundColor: 'white',
      }}>
      <TouchableOpacity onPress={()=>{
        ImagePicker.openPicker({
        }).then(async image => {
          console.log(image);
          const scaleDownImage=await this.ScaleDownImage(image.path);
        
          var ColorImages=this.state.ColorImages;
          ColorImages.push(scaleDownImage);
          this.setState({ColorImages: ColorImages});
          this.SetHue(scaleDownImage,1);
          this.SetHue(scaleDownImage,10);
          this.SetHue(scaleDownImage,50);
          this.SetHue(scaleDownImage,100);
          this.SetHue(scaleDownImage,250);
          this.SetBlue(scaleDownImage);
          this.SetBlur(scaleDownImage);
          this.SetGrayScale(scaleDownImage);
          this.SetGreenScale(scaleDownImage);
          this.SetMirror(scaleDownImage,1);
          this.SetMirror(scaleDownImage,2);
          this.SetNegative(scaleDownImage);
          this.SetRed(scaleDownImage);
          this.SetRotation(scaleDownImage,45);
          this.SetSepia(scaleDownImage);
        }).catch(err=>{
          console.log(err);
        });
      }}>
      <Image source={ 
        this.state.uri=="" ?
        require('./LoadingImage.jpg')
        :
        {
          uri: this.state.uri
        }
      }
      style={{
        width: windowWidth,
        height: windowWidth,
      }}/>
      </TouchableOpacity>
      {
        this.state.uri !="" ? 
        <View>
      <View style={{
        justifyContent: 'center',
        alignItems: 'center',
        margin: '1%'
      }}>
      <Text>Contrast</Text>
      <Slider style={{
        width: 0.7*windowWidth,
        height: 20,
        margin: '1%',
      }} maximumTrackTintColor={'blue'} minimumValue={-100}
      maximumValue={100} 
      value={this.state.Contrast}
       onSlidingComplete={(value)=>{
        this.setState({Contrast: value});
        this.Contrast=value;
        this.FilterImage();
      }}/>
      </View>
      <View style={{
        justifyContent: 'center',
        alignItems: 'center',
        margin: '1%'
      }}>
      <Text>Saturation</Text>
      <Slider style={{
        width: 0.7*windowWidth,
        height: 20,
        margin: '1%',
      }} maximumTrackTintColor={'blue'} minimumValue={-10}
      maximumValue={10} 
      value={this.state.Saturation}
       onSlidingComplete={(value)=>{
         this.setState({Saturation: value});
        this.Saturation=value;
        this.FilterImage();
      }}/>
      </View>
      <View style={{
        justifyContent: 'center',
        alignItems: 'center',
        margin: '1%'
      }}>
      <Text>Brighntess</Text>
      <Slider style={{
        width: 0.7*windowWidth,
        height: 20,
        margin: '1%',
      }} maximumTrackTintColor={'blue'} minimumValue={-0.1}
      maximumValue={10}
      value={this.state.Brighntess}
       onSlidingComplete={(value)=>{
         this.setState({Brighntess: value});
        this.Brighntess=value;
        this.FilterImage();
      }}/>
      </View>
      <View style={{
        justifyContent: 'center',
        alignItems: 'center',
        margin: '1%'
      }}>
      <Text>Sharpen</Text>
      <Slider style={{
        width: 0.7*windowWidth,
        height: 20,
        margin: '1%',
      }} maximumTrackTintColor={'blue'} minimumValue={0}
      maximumValue={10} 
      value={this.state.Sharpen}
       onSlidingComplete={async (value)=>{
         this.setState({Sharpen: value});
       this.Sharpen=value;
       this.FilterImage();
      }}/>
      </View>
      <FlatList horizontal={true} data={this.state.ColorImages} renderItem={(data)=>{
        console.log(data.index);
        return(
          <TouchableOpacity onPress={()=>{
            this.setState({uri: data.item});
            this.setState({originaluri: data.item});
          }}>
          <Image source={{
            uri: data.item
          }} style={{
            width: 0.4*windowWidth,
            height: 0.4*windowWidth
          }}/>
          </TouchableOpacity>
        )
      }}/> 
      </View> : null
    } 
      </View>
   )
         }
 };
 
 export default App;
 