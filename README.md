# react-native-smart-reply

A react native package for smart reply for firebase mlkit

## Installation

```sh
npm install react-native-smart-reply
```

## Usage

```js
import SmartReply from "react-native-smart-reply";

// ...

const result = await SmartReply.generateSmartReplies([{
   isLocalUser: true,
   userId: "1",
   timestamp: new Date(),
   message: "HI",
}]);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
