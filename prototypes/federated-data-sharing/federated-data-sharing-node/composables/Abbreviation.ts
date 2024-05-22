export const useAbreviation = (string: string) : string => {
    const split_names = string.trim().split(" ");
    if (split_names.length > 1) {
        return (split_names[0].charAt(0) + "" + split_names[1].charAt(0));
    }
    return split_names[0].charAt(0);
}