export interface GetOthersDetailsPayload{

  firstName : string
  lastName : string
  username: string
  handles: any
  country: {
    name : string
  }
  requested : boolean
  otherRequested : boolean
  peer: boolean
  reviewed : boolean
  discordTag: string
  profilePicture : {
    id : string
    type : string
    size : number
    url : string
  }
}
