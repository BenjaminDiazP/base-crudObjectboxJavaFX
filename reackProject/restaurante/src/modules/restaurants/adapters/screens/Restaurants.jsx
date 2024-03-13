import { StyleSheet, View, FlatList } from 'react-native'
import React, { useEffect, useState } from 'react';
import FlatListRestaurants from './components/FlatListRestaurants';
import { collection, getDocs, getFirestore } from "firebase/firestore";
import Loading from '../../../../kernel/components/Loading';


export default function Restaurants() {

  /*const restaurants = [
    {
      uid: 1,
      title: "Rincón del Bife",
      description: "Explora una experiencia culinaria única en El Paraíso del Sabor, donde los sabores se fusionan para deleitar tu paladar.",
      rating: 4,
      image: "https://avatars.githubusercontent.com/u/129547979?v=4"
    },
    {
      uid: 2,
      title: "El Marisco",
      description: "Sumérgete en el delicioso mundo del mar en La Taberna del Marisco.",
      rating: 5,
      image: "https://avatars.githubusercontent.com/u/123219894?v=4"
    },
    {
      uid: 3,
      title: "El Jardín",
      description: "Disfruta de una explosión de sabores en El Jardín del Sazón, donde cada plato es una obra maestra culinaria.",
      rating: 3,
      image: "https://avatars.githubusercontent.com/u/126179763?v=4"
    },
    {
      uid: 4,
      title: "La Parrilla",
      description: "Satisface tu pasión por la carne en La Parrilla del Asador, donde el arte del asado se combina con la excelencia culinaria.",
      rating: 4,
      image: "https://avatars.githubusercontent.com/u/85385095?v=4"
    }
  //]*/

  const db = getFirestore();
  const [restaurants, setRestaurants] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const querySnapshot = await getDocs(collection(db, "restaurants"));
        const arrayRestaurants = []
        querySnapshot.forEach((doc) => {
          console.log(`${doc.id} => ${doc.data()}`);
          arrayRestaurants.push(
            {
              uid: doc.id,
              title: doc.data()["title"],
              description: doc.data()["description"],
              rating: doc.data()["rating"],
              image: doc.data()["image"]
            });
          setRestaurants(arrayRestaurants)
        });
      } catch (error) {
        console.log("Error", error);
      } finally {
        setLoading(false);
      }
    })();
  }, []);


  return (

    <View style={styles.container}>
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
        keyExtractor={item => item.uid.toString()}
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