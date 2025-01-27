# SlackComposeMultiplatform (Experimental & In-Development)

### This is a jetpack compose Slack Client & Server Clone written in Kotlin and for Multiplatform.

### Important Points

1. [Protos](https://github.com/oianmol/slack_multiplatform_protos)
2. [GenerateProtos](https://github.com/oianmol/slack_multiplatform_generate_protos)
3. [SlackClientLibrary](https://github.com/oianmol/slack_multiplatform_client_data_lib)
4. [This project](https://github.com/oianmol/slackcomposemultiplatform)
5. [Backend Server in Kotlin](https://github.com/oianmol/slack_multiplatform_grpc_server)
6. [gRPC-KMP Library](https://github.com/oianmol/gRPC-KMP)(Note: On Mac's Use Xcode 13.0 only! 14.0.* doesnt work with grpc!)

## Instructions to compile and get running

Prerequisite

Use the `build.sh` file in the root dir to build everything! make sure to set the ANDROID_SDK_ROOT path in your ENV 

## iOS support with gRPC
Once the gradle sync is successful you need to run the task which deploys the app on simulator. 
There's a specific task to deploy on iPad and iPhones, but composeiOS build fails with ref issue. 
https://github.com/TimOrtel/GRPC-Kotlin-Multiplatform/issues/11

`./gradlew iosDeployIPhone8Debug`

The android and jvm platform run's fine, make sure you match the ip address in GrpcCalls class of your system once you run the slackserver module locally!

## Architecture

![Slack Multiplatform](https://user-images.githubusercontent.com/4393101/195993241-923fc168-aa20-430d-89bd-99e6934b2b08.png)



## Latest Video Demos!


https://user-images.githubusercontent.com/4393101/194718377-7495cb3f-a104-46e3-8ab7-481ec2397e33.mp4


https://user-images.githubusercontent.com/4393101/194718380-d65a3869-4c4a-4409-9ac2-c952dc219b6e.mp4






```
SlackComposeMultiplatformProject
│    
│      
│
└───platform (Android/Desktop/iOS) = Compose multiplatform Desktop
│      
│     
└───generate-proto = Generates the java/kotlin files using protobuf and grpc libs
│  
│     
│   
└───protos = Contains the proto files
│     
│       
│   
└───Slack Server =  the backend server which has the logic for the client app!
```

Video Demo for JVM Desktop with Responsive UI

https://user-images.githubusercontent.com/4393101/188278261-4553ea2b-e80f-4515-be85-e2eba646930b.mp4

Video Demo for Jetpack Compose for iOS

https://user-images.githubusercontent.com/32521663/189109199-6743606c-0e28-4d10-b7ba-61ec3641ed55.mp4


## 🏗️️ Built with ❤️ using Jetpack Compose 😁

| What            | How                        |
|----------------	|------------------------------	|
| 🎭 User Interface (Android,Desktop,iOS)   | [Jetpack Compose](https://developer.android.com/jetpack/compose)                |
| 🏗 Architecture    | [Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)                            |
| 💉 DI (Android)                | [Koin](https://insert-koin.io/)                        |
| 🌊 Async            | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)                |
| 🌐 Networking        | [Ktor.IO](https://ktor.io/)                        |
| 📄 Parsing            | [KotlinX](https://kotlinlang.org/docs/serialization.html)                            |


MIT License

Copyright (c) 2022 Anmol Verma

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
