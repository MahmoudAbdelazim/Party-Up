export interface UpdateUserProfilePayload{
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username: string;
  discordTag: string;
  country: {
    name : string;
  };
}
