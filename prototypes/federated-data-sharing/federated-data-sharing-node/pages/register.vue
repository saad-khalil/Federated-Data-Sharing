<script lang="ts" setup>
    import { Listbox, ListboxButton, ListboxOption, ListboxOptions } from "@headlessui/vue"
    import { CheckIcon } from "@heroicons/vue/24/solid";

    import { useUserStore } from '~/stores/User/userStore';
    import { useOrganizationStore } from '~/stores/Organization/organizationStore';

    const userStore = useUserStore();
    const organizationStore = useOrganizationStore();

    const { register } = userStore
    const { addDummy } = organizationStore

    definePageMeta({
        layout: "auth",
        name: "Federated - Register"
    })

    useHead({
        title: "Federated - Register"
    })

    const isOrganizationModalOpen: Ref<boolean> = ref<boolean>(false)

    const email: Ref<string> = ref<string>('');
    const password: Ref<string> = ref<string>('');
    const name : Ref<string> = ref<string>('');
    const secret_key : Ref<string> = ref<string>('');

    const organization_options = [
        {id: 1, name: "brewery", unavailable: false},
        {id: 2, name: "supermarket", unavailable: false},
        {id: 3, name: "whole_seller", unavailable: false},
    ]

    const security_options = [
        {id: 1, name: "public", unavailable: false},
        {id: 2, name: "private", unavailable: false},
        {id: 3, name: "subscribe", unavailable: false},
    ] as const

    const organization_type : Ref<{
        id: number;
        name: string;
        unavailable: boolean;
    }> = ref(organization_options[0])
    const organization_name : Ref<string> = ref<string>("")
    const organization_security  = ref(security_options[0])

    function generate_new_node(){
        addDummy(organization_name.value, organization_type.value.name, organization_security.value.name)
        isOrganizationModalOpen.value = !isOrganizationModalOpen.value
    }



</script>

<template>
    <div class="max-h-screen">
        <section class="bg-trnasparent min-h-screen flex items-center justify-center">
            <div class="bg-neutral-800 p-5 flex rounded-2xl shadow-3xl border-4 border-emerald-300/10 max-w-3xl">
            <div class="md:w-1/2 px-5 pt-4">
                <div class="flex flex-row items-center justify-start gap-2">
                    <h2 class="text-3xl font-bold text-white">Register</h2>
                </div>
                <div class="mt-6">
                <div>
                    <label class="block text-neutral-300">Email Address</label>
                    <input
                    v-model="email"
                    type="email"
                    name=""
                    id=""
                    placeholder="Enter Email Address"
                    class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
                </div>

                <div class="mt-2">
                    <label class="block text-neutral-300">Name</label>
                    <input
                    v-model="name"
                    type="email"
                    name=""
                    id=""
                    placeholder="Enter First and Last Name"
                    class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
                </div>

                <div class="mt-2">
                    <label class="block text-neutral-300">Node Key</label>
                    <input
                    v-model="secret_key"
                    type="text"
                    name=""
                    id=""
                    placeholder="Enter Node Key"
                    class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
                </div>

                <div class="mt-2">
                    <label class="block text-neutral-300">Password</label>
                    <input
                    v-model="password"
                    type="password"
                    name=""
                    id=""
                    placeholder="Enter Password"
                    v-on:keyup.enter="register({
                        email: email,
                        password: password,
                        name: name,
                        secret_key: secret_key
                    })"
                    minlength="6"
                    class="w-full px-4 py-3 text-neutral-900 rounded-lg bg-neutral-100 mt-2 border-4 focus:border-emerald-400 transition duration-300 focus:bg-white focus:outline-none" required>
                </div>

                <div class="flex flex-row items-center justify-center mt-6">
                    <div
                    @click="register({
                        email: email,
                        password: password,
                        name: name,
                        secret_key: secret_key
                    })"
                    class="w-full block bg-emerald-500 hover:bg-emerald-400 focus:bg-emerald-600 text-white font-semibold rounded-l-md rounded-r-none transition duration-300 px-4 py-3 text-center cursor-pointer">Register</div>
                    <div
                    @click="isOrganizationModalOpen = !isOrganizationModalOpen"
                    class="border-2 border-emerald-500 p-[10px] text-white/40 hover:bg-emerald-400 bg-emerald-500 transition duration-300 hover:text-white"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6">
                            <path fill-rule="evenodd" d="M12 3.75a.75.75 0 01.75.75v6.75h6.75a.75.75 0 010 1.5h-6.75v6.75a.75.75 0 01-1.5 0v-6.75H4.5a.75.75 0 010-1.5h6.75V4.5a.75.75 0 01.75-.75z" clip-rule="evenodd" />
                        </svg>
                    </div>
                    <NuxtLink to="/" class="border-2 border-emerald-500 rounded-r-md p-[10px] text-white/40 hover:bg-emerald-400 bg-emerald-500 transition duration-300 hover:text-white">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6">
                            <path fill-rule="evenodd" d="M7.5 3.75A1.5 1.5 0 006 5.25v13.5a1.5 1.5 0 001.5 1.5h6a1.5 1.5 0 001.5-1.5V15a.75.75 0 011.5 0v3.75a3 3 0 01-3 3h-6a3 3 0 01-3-3V5.25a3 3 0 013-3h6a3 3 0 013 3V9A.75.75 0 0115 9V5.25a1.5 1.5 0 00-1.5-1.5h-6zm5.03 4.72a.75.75 0 010 1.06l-1.72 1.72h10.94a.75.75 0 010 1.5H10.81l1.72 1.72a.75.75 0 11-1.06 1.06l-3-3a.75.75 0 010-1.06l3-3a.75.75 0 011.06 0z" clip-rule="evenodd" />
                        </svg>

                    </NuxtLink>
                </div>
                <LazyModal
                :is-modal-open="isOrganizationModalOpen"
                @update:open="isOrganizationModalOpen = $event"
                >
                    <div class="flex flex-col items-center justify-start text-white w-8/12 mx-auto">
                        <h1 class="font-bold text-[20px]"> Add Organization</h1>

                        <div class="mt-2 w-full">
                            <label class="block text-neutral-300 font-semibold">Organization Name</label>
                            <input
                            v-model="organization_name"
                            type="text"
                            name=""
                            id=""
                            placeholder="Enter Email Address"
                            class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
                        </div>
                        <div class="w-full flex flex-col items-start justify-start py-4 relative">
                            <h1 class="font-semibold  text-neutral-300"> Organization Type</h1>
                            <Listbox v-model="organization_type">
                                <ListboxButton
                                class="w-full px-4 py-3 text-[16px] rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                                > {{ organization_type.name }}
                                </ListboxButton>
                                <Transition
                                enter-active-class="transition duration-100 ease-out"
                                enter-from-class="transform scale-95 opacity-0"
                                enter-to-class="transform scale-100 opacity-100"
                                leave-active-class="transition duration-75 ease-out"
                                leave-from-class="transform scale-100 opacity-100"
                                leave-to-class="transform scale-95 opacity-0"
                                >
                                    <ListboxOptions class="w-full min-w-[310px] bg-neutral-900 rounded-md absolute z-50 p-2 border-2 border-emerald-500/40 shadow-md transform-gpu translate-y-20 bottom-0 flex flex-col items-center justify-start">
                                        <ListboxOption
                                        class=" bg-white rounded-md border-2 border-emerald-500 hover:bg-emerald-500 transition duration-300 w-full py-2  text-black text-center mt-2"
                                        v-for="(type, index) in organization_options"
                                        :key="type.id"
                                        :value="type"
                                        :disabled="type.unavailable"
                                        >
                                            {{ type.name }}
                                        </ListboxOption>
                                    </ListboxOptions>
                                </Transition>
                            </Listbox>
                        </div>

                        <div class="w-full flex flex-col items-start justify-start py-4 relative">
                            <h1 class="font-semibold  text-neutral-300"> Organization Security</h1>
                            <Listbox v-model="organization_security">
                                <ListboxButton
                                class="w-full px-4 py-3 text-[16px] rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                                > {{ organization_security.name }}
                                </ListboxButton>
                                <Transition
                                enter-active-class="transition duration-100 ease-out"
                                enter-from-class="transform scale-95 opacity-0"
                                enter-to-class="transform scale-100 opacity-100"
                                leave-active-class="transition duration-75 ease-out"
                                leave-from-class="transform scale-100 opacity-100"
                                leave-to-class="transform scale-95 opacity-0"
                                >
                                    <ListboxOptions class="w-full min-w-[310px] bg-neutral-900 rounded-md absolute z-10 p-2 border-2 border-emerald-500/40 shadow-md transform-gpu translate-y-20 bottom-0 flex flex-col items-center justify-start">
                                        <ListboxOption
                                        class=" bg-white rounded-md border-2 border-emerald-500 hover:bg-emerald-500 transition duration-300 w-full py-2  text-black text-center mt-2"
                                        v-for="(type, index) in security_options"
                                        :key="type.id"
                                        :value="type"
                                        :disabled="type.unavailable"
                                        >
                                            {{ type.name }}
                                        </ListboxOption>
                                    </ListboxOptions>
                                </Transition>
                            </Listbox>
                        </div>

                        <div
                        @click="generate_new_node()"
                        class="bg-emerald-500 rounded-md py-2 px-10 font-semibold cursor-pointer"
                        >
                            Add
                        </div>
                    </div>

                </LazyModal>
            </div>
        </div>

            <div class="w-1/2 md:block hidden ">
                <img src="https://images.unsplash.com/photo-1614741118887-7a4ee193a5fa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80" class="rounded-2xl" alt="page img">
            </div>

            </div>
        </section>
    </div>
</template>