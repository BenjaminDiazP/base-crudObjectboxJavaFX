import { StyleSheet, Text, View } from 'react-native'
import { getAuth, updateProfile } from "firebase/auth";
import React, {useEffect, useState} from 'react'
import { signOut } from 'firebase/auth';
import { Button, Avatar } from '@rneui/base';
import { set } from 'lodash';
import PhotoProfile from '../components/PhotoProfile';
import ActionProfile from '../components/ActionProfile';

export default function Profile(props) {
  const { navigation } = props;
  const auth = getAuth();
  const user = auth.currentUser;
  const [userProfile, setUserProfile] = useState(null);
  useEffect(() => {
    if(user !== null){
      user.providerData.forEach((profile) => {
        setUserProfile(profile);
      });
    }
  }, []);


  const logout = () => {
    signOut(auth).then(() => {
      navigation.navigate('UserLogged');
    }).catch((error) => {
      console.error(error);
    });
  }
  return (
    <View style={styles.container}>
      {
        userProfile && (<PhotoProfile infoUser={userProfile} />)
      }
      {userProfile && <ActionProfile infoUser={userProfile} />}
      <Button
        title="Cerrar sesión"
        onPress={logout}
        buttonStyle={styles.button}
      />

    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'fff',
    padding: 16
  },
  button: {
    marginTop: 10,
    backgroundColor: 'red',
    width: 250,
    height: 40,
    borderRadius: 8,
  },
})