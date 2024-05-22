import { OrganizationDTO } from "../Organization/Organization"

export type OrganizationAccessDTO = {
    id: string,
    organization: OrganizationDTO,
    allowedOrganizations: OrganizationDTO[]
}