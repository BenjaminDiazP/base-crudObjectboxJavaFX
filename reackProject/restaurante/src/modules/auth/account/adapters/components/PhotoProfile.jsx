import { StyleSheet, Text, View } from "react-native";
import React, { useState } from "react";
import { Avatar } from "@rneui/base";
import * as ImagePicker from "expo-image-picker";
import * as MediaLibrary from 'expo-media-library';
import { getAuth, updateProfile } from "firebase/auth";
import { getStorage, ref, uploadBytes, getDowloadURL } from "firebase/storage";
import UsuarioPhoto from '../../../../../../assets/img/usuario.png'
import Loading from "../../../../../kernel/components/Loading";


export default function PhotoProfile(props) {
  const { infoUser: {uid, photoURL, displayName, email} } = props;
  const [loading, setLoading]= useState(false);
  
  const uploadPhotoUrl = () => {
    const storage = getStorage();
    getDowloadURL(ref(storage, `avatar/${uid}`)).then((url)=>{
        const auth = getAuth();
        updateProfile(auth.currentUser, {
            photoURL: url,
        })
    })
  };

  const uploadImage = async (uri) => {
    try{
      const response = await fetch(uri);      //Fetch ya retorna un blob
      const { blob } = response;
      const storage = getStorage();
      const storageRef = ref(storage, `avatar/${uid}`);
      return uploadBytes(storageRef, blob);
    }catch(error){
      console.log(error);
    }
   
  }

  const changeAvatar = async () => {
    const resultPermission = await MediaLibrary.requestPermissionsAsync();
    if(resultPermission.status === "granted") {
        const result = await ImagePicker.launchCameraAsync({
            allowsEditing: true,
            aspect: [4,3],
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            quality: 1,
            //base64: true
        });
        if (!result.canceled) {
          setLoading(true)
            uploadImage(result.assets[0].uri).then(() => {
                uploadPhotoUrl();
            }).catch((error)=>{
                alert("Error al subir la imagen ", error)
            }).finally(()=>{
              setLoading(false);
            })
        }
    } else {
        alert("Necesitas dar permisos a la cámara")
    }
  }

  return (
    <View style={styles.row}>
      <Avatar
        size={64}
        rounded
        source={photoURL ? { uri: photoURL } : UsuarioPhoto}
      >
        <Avatar.Accessory size={24} onPress={changeAvatar}/>
      </Avatar>
      <View style={styles.column}>
        <Text style={{ fontSize: 14, fontWeight: "bold" }}>
          {displayName || "Anónimo"}
        </Text>
        <Text style={{ fontSize: 12 }}>{email || ''}</Text>
      </View>
      <Loading isShow={Loading} title='Cambiando foto de perfil'/>
    </View>
  );
}

const styles = StyleSheet.create({
  row: {
    flexDirection: "row",
    marginBottom: 16,
    padding: 16,
  },
  column: {
    flexDirection: "column",
    marginLeft: 16,
  },
});