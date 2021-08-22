import { NativeModules } from 'react-native';

type SmartReplyType = {
  multiply(a: number, b: number): Promise<number>;
};

const { SmartReply } = NativeModules;

export default SmartReply as SmartReplyType;
