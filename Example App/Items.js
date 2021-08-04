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
   FlatList
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
 class Items extends Component
 {
   constructor(){
     super();
     this.state={
       xposition: new Animated.Value(0),
       yposition: new Animated.Value(-((windowHeight-windowWidth)/2))
     }
   }
   
   componentDidMount(){
     console.log(windowWidth);
     console.log(windowHeight);
   }
 
   MoveUp=()=>{
     Animated.timing(this.state.yposition,{
       toValue: -windowHeight,
       duration: 3000,
       useNativeDriver: true
     }).start()
   }
   MoveDown=()=>{
     Animated.timing(this.state.yposition,{
       toValue: -windowWidth/2,
       duration: 3000,
       useNativeDriver: true
     }).start()
   }
 
 
   render(){
     
   return (
       <FlatList data={countryList} style={{
           width:windowWidth
       }} renderItem={(data)=>{
         return(
           <Text>{data.item}</Text>
         )
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
 
 export default Items;
 