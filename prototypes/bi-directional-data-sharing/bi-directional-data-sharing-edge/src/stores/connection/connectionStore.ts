import { type RemovableRef, useSessionStorage } from "@vueuse/core";
import { defineStore, storeToRefs } from "pinia";
import { Connection, ConnectionAddress } from "~/types/Connection/Connection";
import { v4 as uuidv4 } from 'uuid';

import { useUserStore } from "../user/userStore";
import { useToast } from "vue-toastification";

export const useConnectionStore = defineStore("authenticatedServer", () => {

    const connections : RemovableRef<Connection[]> = useSessionStorage<Connection[]>("connections", [] as Connection[])

    const userStore = useUserStore();
    const toast = useToast()

    const { user } = storeToRefs(userStore);

    async function authenticate(){
        let address = ""
        let port = 0
        if (process.client) {
            const location = window.location.href
            address = location.substring(7,16)
            port = Number(location.substring(17,21))

            user.value.address = address;
            user.value.port = port;
        }

        setTimeout(() => {
           toast.info("Reading Credentials")
        }, 300);
        const newConnecttion: Connection = <Connection>{
            address: {
                address: address,
                port: port
            },
            server: {
                origin: {
                    name: uuidv4(),
                    id: uuidv4()
                },
                institution: {
                    name: uuidv4(),
                    id: uuidv4()
                }
            }
        } as Connection

        setTimeout(() => {
            toast.info("Created Connections")
        }, 1000);
        const { data } = await useAsyncData<Connection[]>('connections', () => $fetch(`http://${user.value.address}:${user.value.port}/api/authenticate`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: newConnecttion
        }))

        setTimeout(()=>{
            toast.info("Connecting")
        },1700)

        if(data.value == null){
            toast.error("Could not Connect to Server")
            return
        }
        connections.value.push(...data.value)

        setTimeout(() => {
            toast.success("Successfully Connected")
        }, 3700);

        setTimeout(() => {
            navigateTo("/home")
        }, 4000);

    }

    async function connect(address: ConnectionAddress){
        const { data } = await useAsyncData<Connection[]>('connections', () => $fetch(`http://${address.address}:${address.port}/api/connect`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
              },
            body: {
                connection: {
                    source: {
                        address: user.value.address,
                        port: user.value.port
                    },
                    target: address,
                    connections: connections.value
                }
            }
        }));

        if(data === undefined){
            toast.error("Connection Failed")
            return

        }
        if(data.value !== null){
            toast.info("Connection Established")
            setTimeout(() => {
                toast.info("Server Read Successfully")
                connections.value = data.value
            }, 700);
        }
    }
    return {
        connections, connect,authenticate
    }
},
{
    persist: false
})