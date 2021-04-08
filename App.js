/**
 * Payment React Native App
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
  Platform,
  Alert
} from 'react-native';

import queryString from 'query-string';
import base64 from 'react-native-base64';

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
        this.updateOrder();
      }
      else{
        var message = result.error.message;

          if (message){

          }
          else {
              message = "Access not allowed";
          }
        this.showError(message);
      }
    })
    .catch(error => {
      console.log('error', error);
      this.showError(error);
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
    .then(response => {
      if (response.status == 200){
        this.setState( { isLoading : false } );
      }
      else{
        this.showError("No order found");
      }
    })
    .catch(error => {
      console.log('error', error);
      this.showError(error);
    });
  };

  showError(error){
    Alert.alert(
      "",
      error,
      [
        { text: "OK", onPress: () => {
          if (Platform.OS == 'ios') {
            NativeModules.ReactNativeModalBridge.onError();  
          }
          else{
      
          }
        }}
      ]
    )
    
  }

  
// UI part
  render() {
    
    const { isLoading } = this.state;

    return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView style={{ flex:1 }}>
        {this.state.isLoading ? <ActivityIndicator/> : (
          <WebView 
          source={{ uri: "https://uatcheckout.nimbbl.tech/?modal=false&order_id=" + this.props.orderID }} 
          javaScriptEnabled={true} 
          javaScriptCanOpenWindowsAutomatically={true}
          setSupportMultipleWindows={true}
          onMessage={(event)=> {
            console.log("onMessage",event.nativeEvent.data);
          }}
          onNavigationStateChange={(state) => {
            console.log("Navigation object",state);
            
            if (state.url.toLowerCase().includes("https://uatcheckout.nimbbl.tech/mobile/redirect?response=")){
              let params = queryString.parseUrl(state.url);
              let response = params.query.response;
              let decoded = base64.decode(response);
              console.log("Decoded",decoded);
              try {
                let responseData = JSON.parse(decoded);
                let payload = responseData.payload
                console.log("Payload",payload);
                if (Platform.OS == 'ios') {
                  NativeModules.ReactNativeModalBridge.onResponse(payload);  
                }
                else{
    
                }
              } 
              catch (ex) {
                console.error(ex);
              }
              
              
              
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
