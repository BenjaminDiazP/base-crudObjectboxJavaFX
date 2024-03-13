import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { AirbnbRating, Image } from '@rneui/base'

export default function FlatListRestaurants(props) {
    const { image, title, description, rating } = props;
    return (
        <View style={styles.listRestaurant}>
            <Image
                source={{ uri: `${image}` }}
                style={styles.image}
            />
            <View style={styles.containerText}>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
                    <Text style={styles.title}>{title}</Text>
                    <AirbnbRating
                        count={5}
                        isDisabled={true}
                        defaultRating={rating}
                        size={12}
                        showRating={false}
                    />
                </View>
                <Text style={styles.description}>
                    {description}
                </Text>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    listRestaurant: {
        flex: 1,
        flexDirection: "row",
        marginBottom: 16,
        elevation: 2,
        //backgroundColor: '#fff',
        borderRadius: 8,
        width: '100%',
        //alignSelf: "center"
    },
    image: {
        width: 124,
        height: 124,
        borderRadius: 10,
    },
    title: {
        fontSize: 16,
        fontWeight: "bold",
    },
    description: {
        fontSize: 12,
    },
    containerText: {
        flex: 1,
        flexDirection: "column",
        padding: 8,
    },
})