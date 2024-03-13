import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import LoginStack from "../stack/LoginStack";
import RestaurantStack from "../stack/RestaurantStack";
import FavoritesStack from "../stack/FavoritesStack";
import { Icon } from '@rneui/base';
import NetworkStack from "../stack/NetworkStack";

const Tab = createBottomTabNavigator();

export default function Navigation() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarIcon: ({ focused, color, size }) => {
            const { iconName, iconType } = getIconName(route.name, focused);
            return <Icon name={iconName} type={iconType} size={size} color={color} />;
          },
          tabBarActiveTintColor: 'dodgerblue',      //focused en true
          tabBarInactiveTintColor: 'gray',      //focused en false
          headerShown: false
        })}
      >
        <Tab.Screen
          name="RestaurantStack"
          component={RestaurantStack}
          options={{ title: 'Inicio' }}
        />
        <Tab.Screen
          name="FavoritesStack"
          component={FavoritesStack}
          options={{ title: 'Favoritos' }}
        />
        <Tab.Screen
          name="LoginStack"
          component={LoginStack}
          options={{ title: 'Cuenta' }}
        />
        <Tab.Screen
         name="NetworkStack"
         component={NetworkStack}
         options={{ title: 'Networks' }}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
}

const getIconName = (routeName, focused) => {
  let iconName = '';
  let iconType = "material-community";
  //focused es un true o un false que te dice si estas en una pantalla o no (si estas en RestaurantStack su focused regresa un true)
  switch (routeName) {
    case 'RestaurantStack':
      iconName = focused ? 'home' : 'home-outline';
      break;
    case 'FavoritesStack':
      iconName = focused ? 'heart' : 'heart-outline';
      break;
    case 'LoginStack':
      iconName = focused ? 'account' : 'account-outline';
      break;
      case 'NetworkStack':
        iconName = focused ? 'web' : 'web';
        break;
  }
  return { iconName, iconType };
};