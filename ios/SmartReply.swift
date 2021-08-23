import MLKitSmartReply
@objc(SmartReply)
class SmartReply: NSObject {

    // @objc(multiply:withB:withResolver:withRejecter:)
    // func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    //     resolve(a*b)
    // }

    
    @objc(generateSmartReply:withResolver:withRejecter:)
    func generateSmartReply(_ messages: NSArray,resolve:@escaping RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        var conversation: [TextMessage] = []
        var suggestions: [String] = []
        var errorObj: Error?
        let myGroup = DispatchGroup()
        myGroup.enter()
        // Then, for each message sent and received:
        for case let message as NSDictionary in messages {
            let isLocalUser = message["isLocalUser"] as! Bool
            let userId = message["userId"] as! String
            let textMessage = message["message"] as! String
            
            let message = TextMessage(
                text: textMessage,
                timestamp: Date().timeIntervalSince1970,
                userID: userId,
                isLocalUser: isLocalUser)
            conversation.append(message)
        }
        
        MLKitSmartReply.SmartReply.smartReply().suggestReplies(for: conversation) { result, error in
            guard error == nil, let result = result else {
                errorObj = error
                myGroup.leave()
                return
            }
            if (result.status == .notSupportedLanguage) {
                // The conversation's language isn't supported, so
                // the result doesn't contain any suggestions.
                print("Not supported")
                myGroup.leave()
            } else if (result.status == .success) {
                // Successfully suggested smart replies.
                // ...
                print("Found result")
                print(result.suggestions)
                myGroup.leave()
                for suggestion in result.suggestions {
                  print("Suggested reply: \(suggestion.text)")
                    suggestions.append(suggestion.text)
                }
                
            }
        }
        myGroup.notify(queue: DispatchQueue.main){
            resolve(suggestions)
        }
        
        
    }
}
