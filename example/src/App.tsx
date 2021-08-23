import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import SmartReply from 'react-native-smart-reply';

export default function App() {
  // const [result, setResult] = React.useState<Array<String> | undefined>();

  React.useEffect(() => {
    SmartReply.generateSmartReply([
      {
        'message': 'Hello',
        'isLocalUser' : false,
        'userId' : '1'
      },
      {
        'message': 'Hi, how are you?',
        'isLocalUser' : true,
        'userId' : '1'
      },
      {
        'message': 'Im fine, what about you?',
        'isLocalUser' : false,
        'userId' : '1'
      }
    ]).then(v=> console.log(v));
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: 0</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
