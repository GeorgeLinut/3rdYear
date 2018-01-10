import React from 'react';
import {Text,AppRegistry} from 'react-native';
import Header from './src/components/header';

//Create a componen
const App = () =>(
    <Header headerText ={'Albums!'}/>
    );



AppRegistry.registerComponent('auth',()=>App);