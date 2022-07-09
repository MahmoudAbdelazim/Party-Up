export interface ProfileDetailsGetPayload{
  username : string
  email : string
  firstName : string
  lastName : string
  discordTag: string
  handles : {game : string, gameId: number, handle: string, id : number}[]
  profilePicture : {
    id : string
    type : string
    size : number
    url : string
  }
}
