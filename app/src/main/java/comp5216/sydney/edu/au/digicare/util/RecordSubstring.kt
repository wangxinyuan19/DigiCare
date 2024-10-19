package comp5216.sydney.edu.au.digicare.util

// substring first 50 characters
fun displayFirst50Characters(text: String): String {
    return if (text.length > 50) {
        text.substring(0, 50)
    } else {
        text
    }
}