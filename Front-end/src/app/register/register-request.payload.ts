export interface RegisterRequestPayload{
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username: string;
  dataOfBirth: string;
  country: {
    name : string;
  };
}
