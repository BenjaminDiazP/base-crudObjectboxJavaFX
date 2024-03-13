import { StyleSheet, View, FlatList } from 'react-native'
import React, { useEffect, useState } from 'react';
import FlatListRestaurants from '../../../../modules/restaurants/adapters/screens/components/FlatListRestaurants'
import Loading from '../../../../kernel/components/Loading';
import axios from 'axios';
import { Button } from '@rneui/base';

export default function Networks(props) {
 const { navigation } = props;
 const [restaurants, setRestaurants] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const response = await axios.get('http://192.168.107.156:8080/api/restaurant/');
        setRestaurants(response.data.data); // tengo duda  por que si data.data devuelve el array de restaurantes no deberia de hacer el array como en el restaurants.jsx
        // pero pues un asi da el resultado esperado
        console.log("response data", response.data.data);
        console.log("Restaurants", restaurants);
      } catch (error) {
        console.log("Error", error);
      } finally {
        setLoading(false);
      }
    })();
  }, [restaurants]);


  return (

    <View style={styles.container}>
      <Button title="Register" onPress={() => navigation.navigate('Register')} />

      <FlatList
        data={restaurants}
        renderItem={({ item }) =>
          <FlatListRestaurants
            image={item.image}
            title={item.title}
            description={item.description}
            rating={item.rating}
          />
        }
        keyExtractor={item => item.id.toString()}
      />
      <Loading isShow={loading} title="Cargando restaurantes" />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    padding: 16,
  },
});
