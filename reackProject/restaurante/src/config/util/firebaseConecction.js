import { initializeApp, getApp } from "firebase/app";
import {initializeAuth, getAuth,getReactNativePersistence,} from "firebase/auth";
import ReactNativeAsyncStorage from "@react-native-async-storage/async-storage"; 
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: "AIzaSyCcvFf8t_VbtrV8dRT2DuOjsBV6g5AlPxM",
  authDomain: "restaurantb-803f0.firebaseapp.com",
  projectId: "restaurantb-803f0",
  storageBucket: "restaurantb-803f0.appspot.com",
  messagingSenderId: "1051913255158",
  appId: "1:1051913255158:web:f6165b036706514b2e94d7"
};

// initialize Firebase App
const app = initializeApp(firebaseConfig);
// initialize Firebase Auth for that app immediately
const auth = initializeAuth(app, {
  persistence: getReactNativePersistence(ReactNativeAsyncStorage),
});
const db = getFirestore(app);
const storage = getStorage(app);
// Initialize Firebase
export { app, auth, db, storage};
