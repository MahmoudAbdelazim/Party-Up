export interface GetOthersDetailsPayload{
  username: string
  handles: any
  country: {
    name : string
  }
  requested : boolean
  otherRequested : boolean
  peer: boolean
  discordTag: string
  profilePicture : any
}
