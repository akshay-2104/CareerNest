import Navbar from "./Navbar";
import Footer from "./Footer";
import { Outlet } from "react-router-dom";

function Layout() {
  return (
    <div>
      <Navbar />
      <main style={{ minHeight: "80vh", padding: "20px" }}>
        <Outlet /> {/* Page content goes here */}
      </main>
      <Footer />
    </div>
  );
}

export default Layout;
