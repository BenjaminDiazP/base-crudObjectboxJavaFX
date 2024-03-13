import {} from "react";
import { StyleSheet, Text, View } from "react-native";
import Navigation from "./src/config/navigation/Navigation";
import { app, auth, db } from "./src/config/util/firebaseConecction.js";


export default function App() {
  return <Navigation />;
}