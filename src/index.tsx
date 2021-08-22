import { NativeModules } from 'react-native';

// type SmartReplyType = {
//   multiply(a: number, b: number): Promise<number>;
// };

type SmartConvReplyType = {
  generateSmartReply(messages: Array<Object>): Promise<Array<String>>;
};

const { SmartReply } = NativeModules;

export default SmartReply as SmartConvReplyType;
