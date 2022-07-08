export interface ProfileDetailsGetPayload{
  username : string
  email : string
  firstName : string
  lastName : string
  phoneNumber: string
  handles : {game : string, gameId: number, handle: string, id : number}[]
}
