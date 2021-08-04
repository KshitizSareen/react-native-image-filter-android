/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

 import React,{Component} from 'react';
 import {
   Animated,
   SafeAreaView,
   ScrollView,
   StatusBar,
   StyleSheet,
   Text,
   TouchableOpacity,
   useColorScheme,
   View,
   Dimensions,
   FlatList,
   PanResponder,
   Button
 } from 'react-native';
 
 import {
   Colors,
   DebugInstructions,
   Header,
   LearnMoreLinks,
   ReloadInstructions,
 } from 'react-native/Libraries/NewAppScreen';
 
 import {SendMessage} from 'react-native-send-receive-sms-android'
 import countryList from './countries';
 
 const windowWidth=Dimensions.get('window').width;
 
 const windowHeight=Dimensions.get('window').height;
 class ImageList extends Component
 {
   constructor(){
     super();
   }
   
   componentDidMount(){
   }
   FirstItem=0;
   render(){
     
   return (
       <FlatList data={countryList} 
       ref={(ref) => { this.flatListRef = ref; }} renderItem={(data)=>{
         if(data.index>1)
         {
         return(
           <View style={{justifyContent:'center',alignItems: 'center',width:windowWidth,backgroundColor: 'lightblue'}}>
             <Text>{data.item}</Text>
           </View>
         )
         }
         else if (data.index==0)
         {
           return(
             <View style={
               {
                 width: windowWidth,
                 height: windowWidth,
                 backgroundColor: 'blue',
               }
             }>
               
             </View>
             
           )
         }
         else
         {
           return(
             <View style={
               {
                 backgroundColor: 'red',
               }
             }>
               <Text>Select</Text>
             </View>
             
           )
         }
       }} stickyHeaderIndices={[1]} viewabilityConfig={{
         itemVisiblePercentThreshold: 50
       }} onViewableItemsChanged={({ viewableItems, changed }) => {
         this.FirstItem=viewableItems[0].index;
         //ßconsole.log(viewableItems);
       }} onScrollEndDrag={()=>{
         if(this.FirstItem==0 || this.FirstItem==1)
         this.flatListRef.scrollToIndex({animated: true,index: this.FirstItem});
       }}/>
   );
         }
 };
 const styles = StyleSheet.create({
   sectionContainer: {
     marginTop: 32,
     paddingHorizontal: 24,
   },
   sectionTitle: {
     fontSize: 24,
     fontWeight: '600',
   },
   sectionDescription: {
     marginTop: 8,
     fontSize: 18,
     fontWeight: '400',
   },
   highlight: {
     fontWeight: '700',
   },
 });
 
 export default ImageList;
 