<script lang="ts" setup>

    import { useUserStore } from '~/stores/User/userStore';


    const userStore = useUserStore();
    const { authenticate } = userStore

    definePageMeta({
        layout: "auth",
        name: "Federated - Login"
    })

    useHead({
        title: "Federated - Login"
    })

    const email: Ref<string> = ref<string>('');
    const password: Ref<string> = ref<string>('');

</script>

<template>
    <div class="max-h-screen">
        <section class="bg-trnasparent min-h-screen flex items-center justify-center">
            <div class="bg-neutral-800 p-5 flex rounded-2xl shadow-3xl border-4 border-emerald-300/10 max-w-3xl">
            <div class="md:w-1/2 px-5 pt-4">
                <h2 class="text-3xl font-bold text-white">Login</h2>
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

                <div class="mt-4">
                    <label class="block text-neutral-300">Password</label>
                    <input
                    v-model="password"
                    type="password"
                    name=""
                    id=""
                    placeholder="Enter Password"
                    v-on:keyup.enter="authenticate({
                        email: email,
                        password: password
                    })"
                    minlength="6"
                    class="w-full px-4 py-3 text-neutral-900 rounded-lg bg-neutral-100 mt-2 border-4 focus:border-emerald-400 transition duration-300 focus:bg-white focus:outline-none" required>
                </div>

                <div
                @click="authenticate({
                    password: password,
                    email:email
                })"
                class="w-full block bg-emerald-500 hover:bg-emerald-400 focus:bg-emerald-600 text-white font-semibold rounded-lg transition duration-300 px-4 py-3 mt-6 text-center cursor-pointer">Log In</div>
            </div>

                <div class="mt-7 grid grid-cols-3 items-center text-gray-500">
                    <hr class="border-gray-500" />
                    <p class="text-center text-sm">OR</p>
                    <hr class="border-gray-500" />
                </div>

                <div  class="bg-neutral-900 py-2 w-full rounded-md mt-5 flex justify-center items-center text-sm hover:scale-105 duration-300 ">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" class="w-6 h-6" viewBox="0 0 48 48"><defs><path id="a" d="M44.5 20H24v8.5h11.8C34.7 33.9 30.1 37 24 37c-7.2 0-13-5.8-13-13s5.8-13 13-13c3.1 0 5.9 1.1 8.1 2.9l6.4-6.4C34.6 4.1 29.6 2 24 2 11.8 2 2 11.8 2 24s9.8 22 22 22c11 0 21-8 21-22 0-1.3-.2-2.7-.5-4z"/></defs><clipPath id="b"><use xlink:href="#a" overflow="visible"/></clipPath><path clip-path="url(#b)" fill="#FBBC05" d="M0 37V11l17 13z"/><path clip-path="url(#b)" fill="#EA4335" d="M0 11l17 13 7-6.1L48 14V0H0z"/><path clip-path="url(#b)" fill="#34A853" d="M0 37l30-23 7.9 1L48 0v48H0z"/><path clip-path="url(#b)" fill="#4285F4" d="M48 48L17 24l-4-3 35-10z"/></svg>
                    <span class="ml-4 text-white font-semibold">Login with Google</span>
                </div>
                <NuxtLink to="/register" class="bg-emerald-600 py-2 w-full rounded-md mt-5 flex justify-center items-center text-sm hover:scale-105 hover:bg-emerald-500 duration-300 ">
                    <span class="ml-4 text-white font-bold">Register Account</span>
                </NuxtLink>
            </div>

            <div class="w-1/2 md:block hidden ">
                <img src="https://images.unsplash.com/photo-1614741118887-7a4ee193a5fa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80" class="rounded-2xl" alt="page img">
            </div>

            </div>
        </section>
    </div>
</template>