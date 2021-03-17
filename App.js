/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import { WebView } from 'react-native-webview';

import {
  SafeAreaView,
  StatusBar,
  ActivityIndicator,
  NativeModules,
  Platform
} from 'react-native';



class App extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      isLoading: true
    };
  }

  componentDidMount(){
    this.checkAccess();
  }

  // API call
 checkAccess = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    
    var params = JSON.stringify({"access_key":this.props.accessKey,"domain_name":"demo.nimbbl.tech"});
    
    var requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: params
    };
  
    fetch("https://uatapi.nimbbl.tech/api/v2/verify-access-key", requestOptions)
    .then(response => response.json())
    .then(result => {
      console.log(result)
      if (result.access_key_allowed){
        this.updateOrder()
      }
      else{

      }
    })
    .catch(error => {
      console.log('error', error)
      
    });
  };

  updateOrder = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    
    var params = JSON.stringify({"callback_mode":"callback_mobile","callback_url":null});
    
    var requestOptions = {
      method: 'PUT',
      headers: myHeaders,
      body: params
    };
  
    fetch("https://uatapi.nimbbl.tech/api/v2/update-order/" + this.props.orderID, requestOptions)
    .then(response => response.json())
    .then(result => {
      console.log(result)
      this.setState( { isLoading : false } )
    })
    .catch(error => {
      console.log('error', error)
      
    });
  };

  
// UI part
  render() {

    const { isLoading } = this.state;

    return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView style={{ flex:1 }}>
        {this.state.isLoading ? <ActivityIndicator/> : (
          <WebView 
          source={{ uri: 'https://checkout.nimbbl.tech/?modal=false&order_id=' + this.props.orderID }} 
          onMessage={(event)=> console.log(event.nativeEvent.data)}
          onLoadProgress={({ path }) => {
            console.log("current_path",path);      
          }}
          onNavigationStateChange={(state) => {
            console.log("current_url",state.url);  
            if (Platform.OS == 'ios') {
              NativeModules.ReactNativeModalBridge.getUrl(state.url);  
            }
            else{

            }
               
          }} 
          />
        )}
      </SafeAreaView>
    </>
    );
  };
};



export default App;
