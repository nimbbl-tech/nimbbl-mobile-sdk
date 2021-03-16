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
} from 'react-native';



class App extends React.Component {
  render() {
    return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView style={{ flex:1 }}>
        <WebView 
        source={{ uri: this.props.paymentUrl }} 
        onMessage={(event)=> console.log(event.nativeEvent.data)}
        />
      </SafeAreaView>
    </>
    );
  };
};



export default App;
