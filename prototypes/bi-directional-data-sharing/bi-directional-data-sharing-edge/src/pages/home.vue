<script setup lang="ts">
    import { storeToRefs } from 'pinia';
    import { onClickOutside } from '@vueuse/core';
    import { useDatabase } from '~/database/database';
    import { useConnectionStore } from "~/stores/connection/connectionStore"

    import type { Table } from '~/types/Databse/Database';

    definePageMeta({
        name: "BI-Directional Data-Sharing"
    })

    const db = useDatabase();
    const { database } = storeToRefs(db)
    const connStore = useConnectionStore();
    const { connections } = storeToRefs(connStore)

    const modal : Ref<boolean> = ref<boolean>(false);
    const modalRef : Ref<HTMLElement | null> = ref<HTMLElement | null>(null);

    const modalTable : Ref<boolean> = ref<boolean>(false);
    const modalTableRef: Ref<HTMLElement | null> = ref<HTMLElement | null>(null);

    const address: Ref<string> = ref<string>('');
    const port: Ref<string> = ref<string>('');
    const selectedTable : Ref<Table | null>  = ref<Table | null>(null)

    function selectTable(table: Table, index: number): void {
        selectedTable.value = table
    }

    db.create<{
        key: string,
        subject: number
    }>("testing")
    const connectToEdge = async () => {
        await connStore.connect({ address: "127.0.0.1", port: 3000})

    }

    onClickOutside(
        modalRef,
        (event) => {
            modal.value = false
        },
    )


</script>

<template>
    <div class="w-full h-full overflow-hidden flex flex-row">
        <div class="w-3/12 shadow-lg h-full flex flex-col items-center justify-start pt-4">
            <div class="bg-neutral-800 rounded-lg shadow-xl h-[700px] w-11/12 flex flex-col items-center justify-start ">
                <div class="flex flex-row items-center justify-between w-11/12">
                    <h1 class="text-emerald-500 font-semibold text-[20px] pb-4 text-left pt-3">Connections</h1>
                    <div
                    @click="modal = !modal"
                    class="bg-neutral-900 rounded-md text-emerald-400/60 hover:text-emerald-400 font-semibold py-1 w-5/12 text-center border border-emerald-600/20 hover:border-emerald-400 tansition duration-500 cursor-pointer">
                        Connect
                    </div>
                    <Transition>
                    <div
                    v-if="modal"
                    ref="modalRef"
                    class="absolute bg-neutral-900 border border-emerald-400/20  max-w-lg min-w-fit w-full right-40 shadow-md h-[24rem] p-4 text-center transform -translate-x-1/2 top-1/4 left-1/2 flex flex-col items-center justify-center rounded-md">
                        <h1 class= "font-semiold text-[25px] text-emerald-400 pb-3">Connect to another Edge</h1>
                        <div class="mb-6 flex flex-col items-start">
                            <label for="metaTitle" class="block mb-2 text-sm font-semibold text-emerald-400/80 ">Address</label>
                            <input
                            type="text"
                            v-model="address"
                            id="metaTitle"
                            class="bg-neutral-800 border border-emerald-400/50 font-semibold text-sm rounded-lg focus:ring-emerald-400 focus:border-emerald-400 block p-2.5 mx-auto  w-full outline-none text-white transition duration-500">
                        </div>
                        <div class="mb-6 flex flex-col items-start">
                            <label for="metaTitle" class="block pb-2 text-sm font-semibold text-emerald-400/80 ">Port</label>
                            <input
                            type="text"
                            v-model="port"
                            id="metaTitle"
                            class="bg-neutral-800 border border-emerald-400/50 font-semibold text-sm rounded-lg focus:ring-emerald-400 focus:border-emerald-400 block p-2.5 mx-auto  w-full outline-none text-white transition duration-500">
                        </div>
                        <div
                        @click="connStore.connect({
                            address: address,
                            port: Number(port)
                        })"
                        class="bg-neutral-800 rounded-lg px-6 py-2 text-white mx-auto hover:bg-emerald-500 border border-emerald-400/40 transition duration-500">
                            Connect
                        </div>
                    </div>
                </Transition>
                </div>
                <div class="w-full h-full overflow-y-auto no-scrollbar flex flex-col items-center justify-start gap-3 pt-4">
                    <div
                    v-for="(connection, index) in connections"
                    :key="index"
                    class="flex flex-col items-start justify-between gap-2 bg-transparent w-full border border-emerald-400/20 rounded-md p-2 group text-white/50 hover:border-emerald-400 transition duration-300"
                    >
                        <h1 class="flex flex-row gap-2 items-center justify-start bg-neutral-900 py-2 px-2 rounded font-semibold h-full text-center w-full group-hover:text-white duration-300 transition">
                            IID: {{ connection.server.institution.id }}
                        </h1>
                        <h1 class="flex flex-row gap-2 items-center justify-start bg-neutral-900 py-2 px-2 rounded font-semibold h-full text-center w-full group-hover:text-white duration-300 transition">
                            OID: {{ connection.server.origin.id }}
                        </h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="w-6/12 h-full flex flex-col items-center justify-start pt-4">
            <div class="flex flex-col bg-neutral-800 h-[800px] w-full gap-2 p-4 overflow-y-scroll no-scrollbar">
                <div class="flex flex-row items-center justify-between w-full">
                    <h1 class="w-full text-[20px] text-left pb-2 text-emerald-400 font-semibold">Tables</h1>

                    <div class="bg-transparent p-2 rounded border-2 border-emerald-400/20 hover:bg-emerald-500 hover:border-emerald-400 transition duration-500 text-white">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
                        </svg>
                    </div>
                </div>
                <div
                v-for="(table, index) in database.tables"
                :key="index"
                @click="selectTable(table,index)"
                class="w-full border-2 border-emerald-400/20 rounded-md p-4 hover:border-emerald-400 transition duration-500 text-white"
                >
                    <h1 class="text-[20px]">Table: {{ table.name }}</h1>
                    <button>click</button>
                </div>

            </div>
        </div>
        <div class="w-3/12 h-full flex flex-col items-center justify-start pt-4">
            <div class="bg-neutral-800 rounded-lg shadow-xl h-[700px] w-11/12 flex flex-col items-center justify-start p-3 ">
                <div class="flex flex-row items-center justify-between w-full">
                    <h1 class="text-emerald-500 font-semibold text-[20px] pb-4 w-full text-left">{{selectedTable?.name ? `Contents: ${selectedTable?.name}` : "Select a table" }}</h1>
                    <div class="bg-transparent p-2 rounded border-2 border-emerald-400/20 hover:bg-emerald-500 hover:border-emerald-400 transition duration-500 text-white">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
                        </svg>
                    </div>
                </div>
                <div class="w-full h-full overflow-y-auto no-scrollbar flex flex-col items-center justify-start gap-3">
                    <div
                    v-for="(row, index) in selectedTable?.rows"
                    :key="index"
                    class="flex flex-col items-start justify-between gap-2 bg-transparent w-full border border-emerald-400/20 rounded-md p-2 group text-white/50 hover:border-emerald-400 transition duration-300"
                    >
                        <h1 class="flex flex-row gap-2 items-center justify-start bg-neutral-900 py-2 px-2 rounded font-semibold h-full text-center w-full group-hover:text-white duration-300 transition">
                            ID: {{ row.id }}
                        </h1>
                        <h1 class="flex flex-row gap-2 items-center justify-start bg-neutral-900 py-2 px-2 rounded font-semibold h-full text-center w-full group-hover:text-white duration-300 transition">
                            Entry: {{ row.entry }}
                        </h1>
                        <h1 class="flex flex-row gap-2 items-center justify-start bg-neutral-900 py-2 px-2 rounded font-semibold h-full text-center w-full group-hover:text-white duration-300 transition">
                            Count: {{ row.count}}
                        </h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.no-scrollbar::-webkit-scrollbar {
    display: none;
}

.no-scrollbar {
    -ms-overflow-style: none;
    scrollbar-width: none;
}


.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>