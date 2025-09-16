import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Home from "./components/Home";
import About from "./components/About";
import Login from "./components/Login";

function App() {
  return (
    <Routes>
      {/* Layout wraps all pages */}
      <Route path="/" element={<Layout />}>
        {/* Default page */}
        <Route index element={<Home />} />
        <Route path="about" element={<About />} />
        <Route path="login" element={<Login />} />
      </Route>
    </Routes>

  );
}

export default App;
