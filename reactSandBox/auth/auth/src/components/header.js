import React from 'react';
import {Text, View} from 'react-native';


const Header = (props) => {
    const {textStyle,viewStyle} = stytles;
    return (<View style = {viewStyle}>
        <Text style={textStyle}>{props.headerText}</Text>
    </View>);
};

const stytles = {
    viewStyle: {
        backgroundColor : '#F8F8F8'
    },
    textStyle: {
        fontStyle: 20
    }
}

export default Header;