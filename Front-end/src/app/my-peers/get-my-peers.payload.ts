export interface GetMyPeersPayload{
  username: string;
  profilePicture : {
    id : string
    type : string
    size : number
    url : string
  }
}
