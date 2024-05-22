
export type FederatedNodeResponse = {
    message: string,
    success: boolean,
    payload: FederatedNode | null | unknown
}

export type DBFederatedNode = {
    node_id?: number,
    name: string,
    security : string,

}

export type FederatedNode = DBFederatedNode & {
    subscribers: DBFederatedNode[]
    subscribed: DBFederatedNode[]
}