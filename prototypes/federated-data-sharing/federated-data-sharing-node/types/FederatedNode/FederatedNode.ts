import { Organization } from "../Organization/Organization";
import { Privacy } from "../Privacy/Privacy";

export type FederatedNode = {
    id: number,
    subscribers: Node[],
    owner: Organization,
} & Privacy