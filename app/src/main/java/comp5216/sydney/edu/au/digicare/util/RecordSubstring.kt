package comp5216.sydney.edu.au.digicare.util

// substring first 50 characters
fun displayFirst50Characters(text: String): String {
    return if (text.length > 150) {
        text.substring(0, 150)
    } else {
        text
    }
}