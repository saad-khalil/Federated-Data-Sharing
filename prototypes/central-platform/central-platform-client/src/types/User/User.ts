export type UserCredentials = {
  email: string;
  password: string;
};

export type User = {
  id: string;
  firstName: string;
  lastName: string;
  organizationId: string;
  role: string;
  token: string;
} & UserCredentials;

export type MinifiedUser = Omit<User, "id" | "token" | 'role'>;
