import { Organization } from "../Organization/Organization"

export type DBUser = {
    email: string,
    organization_id: number,
    name : string,
    password: string

}

export type User = DBUser & {
    user_id: number,
}

export type BasicResponse = {
    message : string,
    success: boolean
}

export type UserResponse = {
    payload: User
} & BasicResponse

export type UserAuthenticationResponse = {
    payload: {
        user: User,
        organization: Organization
        organization_subscribers: Organization[]
    } | null
} & BasicResponse