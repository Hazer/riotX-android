Useful links:
- https://codelabs.developers.google.com/codelabs/webrtc-web/#0


   ╔════════════════════════════════════════════════╗
   ║                                                ║
   ║A] Placing a call offer                         ║
   ║                                                ║
   ╚════════════════════════════════════════════════╝



                                                    ┌───────────────┐
                                                    │    Matrix     │
                                                    ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
                                                                                                                     │
                                                    │
                                                                                                                     │
              ┌─────────────────┐                   │                  ┌───────────────────────────┐                                      ┌─────────────────┐
              │     Caller      │                                      │      Signaling Room       │                 │                    │     Callee      │
              └─────────────────┘                   │                  ├───────────────────────────┤                                      └─────────────────┘
                                       ┌────┐                          │                           │                 │
                                       │ 3  │       │                  │  ┌────────────────────┐   │
              ┌─────────────────┐──────┴────┴──────────────────────────┼─▶│   m.call.invite    │   │                 │                    ┌─────────────────┐
              │                 │                   │                  │  │      mx event      │   │                                      │                 │
              │                 │                                      │  └────────────────────┘   │                 │                    │                 │
              │                 │                   │                  │                           │                                      │                 │
              │     Riot.im     │                                      │                           │                 │                    │     Riot.im     │
           ┌──│       App       │                   │                  │                           │                                      │       App       │
           │  │                 │                                      │                           │                 │                    │                 │
           │  │                 │                   │                  │                           │                                      │                 │
           │  │                 │                                      │                           │                 │                    │                 │
           │  └─────────────────┘                   │                  │                           │                                      └─────────────────┘
      ┌────┤     ▲                                                     │                           │                 │
      │ 1  │     ├────┐                             │                  └───────────────────────────┘
      └────┤     │ 2  │                              ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
           │  ┌──┴────┴─────────┐                                                                                                         ┌─────────────────┐
           │  │                 │                                                                                                         │                 │
           │  │                 │                                                                                                         │                 │
           │  │     WebRtc      │                                                                                                         │     WebRtc      │
           └─▶│                 │                                                                                                         │                 │
              │                 │                                                                                                         │                 │
              │                 │                                                                                                         │                 │
              └─────────────────┘                                                                                                         └─────────────────┘









       ┌────┐
       │ 1  │  The Caller app get access to system resources (camera, mic), eventually stun/turn servers, define some
       └────┘  constrains (video quality, format) and pass it to WebRtc in order to create a Peer Call offer

       ┌────┐
       │ 2  │  The WebRtc layer creates a call Offer (sdp) that needs to be sent to callee
       └────┘

       ┌────┐  The app layer, takes the webrtc offer, encapsulate it in a matrix event adds a callId and send it to the other
       │ 3  │  user via the room
       └────┘
                   ┌──────────────┐
                   │   mx event   │
                   ├──────────────┴────────┐
                   │ type: m.call.invite   │
                   │ + callId              │
                   │                       │
                   │ ┌──────────────────┐  │
                   │ │    webrtc sdp    │  │
                   │ └──────────────────┘  │
                   └───────────────────────┘







   ╔════════════════════════════════════════════════╗
   ║                                                ║
   ║B] Sending connection establishment info        ║
   ║                                                ║
   ╚════════════════════════════════════════════════╝



                                                   ┌───────────────┐
                                                   │    Matrix     │
                                                   ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
                                                                                                                    │
                                                   │
                                                                                                                    │
             ┌─────────────────┐                   │                  ┌───────────────────────────┐                                      ┌─────────────────┐
             │     Caller      │                                      │      Signaling Room       │                 │                    │     Callee      │
             └─────────────────┘                   │                  ├───────────────────────────┤                                      └─────────────────┘
                                                                      │  ┌────────────────────┐   │                 │
                                                   │                  │  │   m.call.invite    │   │
             ┌─────────────────┐                                      │  │      mx event      │   │                 │                    ┌─────────────────┐
             │                 │      ┌────┐       │                  │  └────────────────────┘   │                                      │                 │
             │                 │      │ 3  │                          │  ┌────────────────────┐   │                 │                    │                 │
             │                 │──────┴────┴───────┼──────────────────┼─▶│ m.call.candidates  │   │                                      │                 │
             │     Riot.im     │                                      │  │      mx event      │   │                 │                    │     Riot.im     │
             │       App       │                   │                  │  └────────────────────┘   │                                      │       App       │
             │                 │                                      │                           │                 │                    │                 │
             │                 │                   │                  │                           │                                      │                 │
             │                 │                                      │                           │                 │                    │                 │
             └─────────────────┘                   │                  │                           │                                      └─────────────────┘
                     ▲                                                │                           │                 │
                     ├────┐                        │                  └───────────────────────────┘
                     │ 2  │                         ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
             ┌───────┴────┴────┐                                                                                                         ┌─────────────────┐
             │                 │                                                                                                         │                 │
             │                 │                                                                                                         │                 │
             │     WebRtc      │                   ┌───────────────┐                                                                     │     WebRtc      │
             │                 │                   │  Stun / Turn  │                                                                     │                 │
             │                 │                   ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                                         │                 │
             │                 │                                                                │                                        │                 │
             └─────────────────┘                   │                                                                                     └─────────────────┘
                      ▲                                                                         │
                      │                            │
                      └──────────┬────┬───────────▶                                             │
              ┌───────────────┐  │ 1  │            │
              │               │  └────┘                                                         │
              │ Network Stack │                    │
              │               │                     ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
              │               │
              └───────────────┘




       ┌────┐
       │ 1  │  The WebRtc layer gathers information on how it can be reach by the other peer directly (Ice candidates)
       └────┘
               ┌──────────────────────────────────────────────────────────────────┐
               │candidate:1 1 tcp 1518149375 127.0.0.1 35990 typ host             │
               └──────────────────────────────────────────────────────────────────┘
               ┌──────────────────────────────────────────────────────────────────┐
               │candidate:2 1 UDP 2130706431 192.168.1.102 1816 typ host          │
               └──────────────────────────────────────────────────────────────────┘

       ┌────┐
       │ 2  │  The WebRTC layer notifies the App layer when it finds new Ice Candidates
       └────┘



       ┌────┐  The app layer, takes the ice candidates, encapsulate them in one or several matrix event adds the callId and
       │ 3  │  send it to the other user via the room
       └────┘
                   ┌──────────────┐
                   │   mx event   │
                   ├──────────────┴────────────────────────┐
                   │  type: m.call.candidates              │
                   │                                       │
                   │  +CallId                              │
                   │                                       │
                   │ ┌──────────────────┐                  │
                   │ │ice candidate sdp │                  │
                   │ └──────────────────┘                  │
                   │ ┌──────────────────┐                  │
                   │ │ice candidate sdp │                  │
                   │ └──────────────────┘                  │
                   │ ┌──────────────────┐                  │
                   │ │ice candidate sdp │                  │
                   │ └──────────────────┘                  │
                   └───────────────────────────────────────┘





    ╔════════════════════════════════════════════════╗
    ║                                                ║
    ║C] Receiving a call offer                       ║
    ║                                                ║
    ╚════════════════════════════════════════════════╝



                                                    ┌───────────────┐
                                                    │    Matrix     │
                                                    ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
                                                                                                                     │
                                                    │                                                                                     ┌─────────────────┐
                                                                                                                     │                    │     Callee      │
              ┌─────────────────┐                   │                  ┌───────────────────────────┐                                      └─────────────────┘
              │     Caller      │                                      │      Signaling Room       │                 │
              └─────────────────┘                   │                  ├───────────────────────────┤
                                                                       │  ┌────────────────────┐   │                 │                    ┌─────────────────┐
                                                    │                  │  │   m.call.invite    │───┼────────────────────────────┬────┬───▶│                 │
              ┌─────────────────┐                                      │  │      mx event      │   │                 │          │ 1  │    │                 │
              │                 │                   │                  │  └────────────────────┘   │                            └────┘    │                 │
              │                 │                                      │  ┌────────────────────┐   │                 │                    │     Riot.im     │
              │                 │                   │                  │  │ m.call.candidates  │   │                                      │       App       │
              │     Riot.im     │                                      │  │      mx event      │   │                 │                    │                 │
              │       App       │                   │                  │  └────────────────────┘   │                                      │                 │
              │                 │                                      │  ┌────────────────────┐◀──┼─────────────────┼───┬────┬───────────┤                 │
              │                 │◀──────────────────┼──────────────────┼──│   m.call.answer    │   │                     │ 4  │           └──┬──────────────┘
              │                 │                                      │  │      mx event      │   │                 │   └────┘              ├────┐    ▲
              └────┬────────────┘                   │                  │  └────────────────────┘   │                                         │ 2  │    ├────┐
                   │                                                   │                           │                 │                       ├────┘    │ 3  │
                   │                                │                  └───────────────────────────┘                                      ┌──▼─────────┴────┤
              ┌────▼────────────┐                    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘                    │                 │
              │                 │                                                                                                         │                 │
              │                 │                                                                                                         │     WebRtc      │
              │     WebRtc      │                                                                                                         │              ┌──┴─────────────────┐
              │                 │                                                                                                         │              │    caller offer    │
   ┌──────────┴─────────┐       │                                                                                                         │              └──┬─────────────────┘
   │   callee answer    │       │                                                                                                         └─────────────────┘
   └────────────────────┴───────┘







        ┌────┐
        │ 1  │  Bob receives a call.invite event in a room, then creates a WebRTC peer connection object
        └────┘

        ┌────┐
        │ 2  │  The encapsulated call offer sdp from the mx event is transmitted to WebRTC
        └────┘

        ┌────┐
        │ 3  │  WebRTC then creates a call answer for the offer and send it back to app layer
        └────┘


        ┌────┐  The app layer, takes the webrtc answer, encapsulate it in a matrix event adds a callId and send it to the
        │ 3  │  other user via the room
        └────┘
                    ┌──────────────┐
                    │   mx event   │
                    ├──────────────┴────────┐
                    │ type: m.call.answer   │
                    │ + callId              │
                    │                       │
                    │ ┌──────────────────┐  │
                    │ │    webrtc sdp    │  │
                    │ └──────────────────┘  │
                    └───────────────────────┘






    ╔════════════════════════════════════════════════╗
    ║                                                ║
    ║D] Callee sends connection establishment info   ║
    ║                                                ║
    ╚════════════════════════════════════════════════╝



                                                    ┌───────────────┐
                                                    │    Matrix     │
                                                    ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
                                                                                                                     │
                                                    │
                                                                                                                     │
              ┌─────────────────┐                   │                  ┌───────────────────────────┐                                      ┌─────────────────┐
              │     Caller      │                                      │      Signaling Room       │                 │                    │     Callee      │
              └─────────────────┘                   │                  ├───────────────────────────┤                                      └─────────────────┘
                                                                       │  ┌────────────────────┐   │                 │
                                                    │                  │  │   m.call.invite    │   │
              ┌─────────────────┐                                      │  │      mx event      │   │                 │                    ┌─────────────────┐
              │                 │                   │                  │  └────────────────────┘   │                                      │                 │
              │                 │                                      │  ┌────────────────────┐   │                 │                    │                 │
              │                 │                   │                  │  │ m.call.candidates  │   │                                      │                 │
              │     Riot.im     │                                      │  │      mx event      │   │                 │                    │     Riot.im     │
              │       App       │                   │                  │  └────────────────────┘   │                        ┌────┐        │       App       │
              │                 │                                      │  ┌────────────────────┐   │                 │      │ 3  │        │                 │
              │                 │◀──────────────────┼┐                 │  │   m.call.answer    │   │                ┌───────┴────┴────────│                 │
              │                 │                    │                 │  │      mx event      │   │                ││                    │                 │
              └─────────────────┘                   ││                 │  └────────────────────┘   │                │                     └─────────────────┘
                   │                                 │                 │  ┌────────────────────┐   │                ││                         ▲
                   │                                │└─────────────────┼──│ m.call.candidates  │   │                │                          ├────┐
                   ▼                                                   │  │      mx event      │◀──┼────────────────┘│                         │ 2  │
              ┌─────────────────┐                   │                  │  └────────────────────┘   │                                      ┌────┴────┴───────┐
              │                 │                                      └───────────────────────────┘                 │                    │                 │
              │                 │                   │                                                                                     │                 │
              │     WebRtc      │                                                                                    │                    │     WebRtc      │
              │                 │                   └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                     │             ┌───┴────────────────┐
              │                 │                                                                                                         │             │    caller offer    │
     ┌────────┴───────────┐     │                                                                                                         │             └───┬────────────────┘
     │   callee answer    ├─────┘                                                  ┌───────────────┐                                      └─────────────────┘
     ├────────────────────┤                                                        │  Stun / Turn  │                                               ▲
     │     callee ice     │                                                        ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─                       ┌────┐  │
     │     candidates     │                                                                                         │                      │ 1  │  │
     └────────────────────┘                                                        │                                                       ├────┴──┴───────┐
                                                                                                                    │                      │               │
                                                                                   │                                                       │ Network Stack │
                                                                                                                    │◀─────────────────────┤               │
                                                                                   │                                                       │               │
                                                                                                                    │                      └───────────────┘
                                                                                   │
                                                                                    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘


        ┌────┐
        │ 1  │  The WebRtc layer gathers information on how it can be reach by the other peer directly (Ice candidates)
        └────┘
                ┌──────────────────────────────────────────────────────────────────┐
                │candidate:1 1 tcp 1518149375 127.0.0.1 35990 typ host             │
                └──────────────────────────────────────────────────────────────────┘
                ┌──────────────────────────────────────────────────────────────────┐
                │candidate:2 1 UDP 2130706431 192.168.1.102 1816 typ host          │
                └──────────────────────────────────────────────────────────────────┘

        ┌────┐
        │ 2  │  The WebRTC layer notifies the App layer when it finds new Ice Candidates
        └────┘



        ┌────┐  The app layer, takes the ice candidates, encapsulate them in one or several matrix event adds the callId and
        │ 3  │  send it to the other user via the room
        └────┘
                    ┌──────────────┐
                    │   mx event   │
                    ├──────────────┴────────────────────────┐
                    │  type: m.call.candidates              │
                    │                                       │
                    │  +CallId                              │
                    │                                       │
                    │ ┌──────────────────┐                  │
                    │ │ice candidate sdp │                  │
                    │ └──────────────────┘                  │
                    │ ┌──────────────────┐                  │
                    │ │ice candidate sdp │                  │
                    │ └──────────────────┘                  │
                    │ ┌──────────────────┐                  │
                    │ │ice candidate sdp │                  │
                    │ └──────────────────┘                  │
                    └───────────────────────────────────────┘






     ╔════════════════════════════════════════════════╗
     ║                                                ║
     ║D] Caller Callee connection                     ║
     ║                                                ║
     ╚════════════════════════════════════════════════╝








                                                   ┌───────────────┐
               ┌─────────────────┐                 │    Matrix     │                                                                       ┌─────────────────┐
               │     Caller      │                 ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                       │     Callee      │
               └─────────────────┘                                                                                  │                      └─────────────────┘
                                                   │
                                                                                                                    │
               ┌─────────────────┐                 │                                                                                       ┌─────────────────┐
               │                 │                                                                                  │                      │                 │
               │                 │                 │                                                                                       │                 │
               │                 │                                                                                  │                      │                 │
               │     Riot.im     │                 └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                       │     Riot.im     │
               │       App       │                                                                                                         │       App       │
               │                 │                                                                                                         │                 │
               │                 │                                                                                                         │                 │
               │                 │                                                                                                         │                 │
               └─────────────────┘                                                                                                         └─────────────────┘
                                                    ┌───────────────┐
                                                    │   Internet    │
                                                    ├───────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
               ┌─────────────────┐                                                                                   │                     ┌─────────────────┐
               │                 │                  │                                                                                      │                 │
               │                 ├───────────────────────────────────────────────────────────────────────────────────┴─────────────────────┤                 │
               │     WebRtc      │█████████████████████████████████████████████████████████████████████████████████████████████████████████│     WebRtc      │
 ┌─────────────┴──────┐          ├────────────────────────────────────────┬──────────────────────────┬───────────────┬─────────────────────┤           ┌─────┴──────────────┐
 │   callee answer    │          │                  │                     │   Video / Audio Stream   │                                     │           │    caller offer    │
 ├────────────────────┤          │                                        └──────────────────────────┘               │                     │           ├────────────────────┤
 │     callee ice     ├──────────┘                  └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                      └───────────┤     caller ice     │
 │     candidates     │                                                                                                                                │     candidates     │
 └────────────────────┘                                                                                                                                └────────────────────┘



                                                         ┌─────────────────────────────────────────────────────┐
                                                         │                                                     │░
                                                         │ If connection is impossible (firewall), and a turn  │░
                                                         │server is available, connection could happen through │░
                                                         │                       a relay                       │░
                                                         │                                                     │░
                                                         └─────────────────────────────────────────────────────┘░
                                                          ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░


                                                     ┌───────────────┐
                                                     │   Internet    │
                                                     └─┬─────────────┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
                ┌─────────────────┐                                                                                     │                   ┌─────────────────┐
                │                 │                    │                   ┌─────────────────────────┐                                      │                 │
                │                 ├───────────────────────────────────────┐│                         │                  │                   │                 │
                │     WebRtc      │███████████████████████████████████████││                         │                                      │     WebRtc      │
                │                 ├───────────────────────────────────────┘│                         │                  │                   │                 │
                │                 │           ┌────────┴─────────────────┐ │          Relay          │┌─────────────────────────────────────┤                 │
┌───────────────┴────┐            │           │   Video / Audio Stream   │ │                         ││█████████████████████████████████████│         ┌───────┴────────────┐
│   callee answer    ├────────────┘           └────────┬─────────────────┘ │                         │└─────────────────────────────────────┴─────────┤    caller offer    │
├────────────────────┤                                                     │                         │                  │                             ├────────────────────┤
│     callee ice     │                                 │                   │                         │                                                │     caller ice     │
│     candidates     │                                                     └─────────────────────────┘                  │                             │     candidates     │
└────────────────────┘                                 │                                                                                              └────────────────────┘
                                                                                                                        │
                                                       │
                                                                                                                        │
                                                       │
                                                                                                                        │
                                                       └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─