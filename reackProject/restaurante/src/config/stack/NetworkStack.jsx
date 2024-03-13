import React from 'react'
import { createStackNavigator } from "@react-navigation/stack";
import Networks from '../../modules/network/adapters/screens/Networks';
import Register from '../../modules/network/adapters/screens/components/Register';

const Stack = createStackNavigator();

export default function NetworkStack() {
  return (
    <Stack.Navigator>
        <Stack.Screen
            name="Networks"
            component={Networks}
            options={{ title: 'Network' }}
        />
         <Stack.Screen
            name="Register"
            component={Register}
            options={{ title: 'Register' }}
        />
    </Stack.Navigator>
  )
}