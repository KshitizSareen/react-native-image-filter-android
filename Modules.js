import { NativeModules } from 'react-native';

const { ImageFilters } = NativeModules;

const FilterImage={
    MirrorImage:  async function (ImageUri,Type){
        const res=await ImageFilters.MirrorImage(ImageUri,Type);
        return res;
    },
    RotateImage: async function (ImageUri,Rotation){
        const res=await ImageFilters.RotateImage(ImageUri,Rotation);
        return res;
    },
    SharpenImage: async function (ImageUri,SharpenBy){
        const res=await ImageFilters.SharpenImage(ImageUri,SharpenBy);
        return res;
    },
    ScaleDownImage: async function (ImageUri,Scale){
        const res=await ImageFilters.ScaleDownImage(ImageUri,Scale);
        return res;
    },
    SetSepiaFilter: async function (ImageUri){
        const res=await ImageFilters.SetSepiaFilter(ImageUri);
        return res;
    },
    SetHue: async function (ImageUri,Hue){
        const res=await ImageFilters.SetHue(ImageUri,Hue);
        return res;
    },
    SetBlurFilter: async function (ImageUri){
        const res=await ImageFilters.SetBlurFilter(ImageUri);
        return res;
    },
    SetContrast: async function (ImageUri,Contrast){
        const res=await ImageFilters.SetContrast(ImageUri,Contrast);
        return res;
    },
    SetBlueFilter: async function (ImageUri){
        const res=await ImageFilters.SetBlueFilter(ImageUri);
        return res;
    },
    SetGreenFilter: async function(ImageUri){
        const res=await ImageFilters.SetGreenFilter(ImageUri);
        return res;
    },
    SetRedFilter: async function(ImageUri){
        const res=await ImageFilters.SetRedFilter(ImageUri);
        return res;
    },
    SetBrightness: async function(ImageUri,Brightness){
        const res=await ImageFilters.SetBrightness(ImageUri,Brightness);
        return res;
    },
    SetSaturation: async function(ImageUri,Saturation){
        const res=await ImageFilters.SetSaturation(ImageUri,Saturation);
        return res;
    },
    SetNegative: async function(ImageUri){
        const res=await ImageFilters.SetNegative(ImageUri);
        return res;
    },
    SetGrayScale: async function(ImageUri){
        const res=await ImageFilters.SetGrayScale(ImageUri);
        return res;
    }
}

export default FilterImage;