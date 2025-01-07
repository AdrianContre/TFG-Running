import { useState } from "react";
import { useEffect } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { getPlanResults } from "../services/trainingResultService";
import ResultsList from "./ResultsList";
import Paginator from "../../../Paginator";

function TrainingProgress() {
    const location = useLocation()
    const {planId} = location.state
    const [results, setResults] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
    const [plansPerPage] = useState(10);
    useEffect(() => {
        const fetchInfo = async () => {
            const results = await getPlanResults(planId)
            console.log(results)
            setResults(results)
            
        }
        fetchInfo()
    },[])

    const totalPages = Math.ceil(results.length / plansPerPage);
    const indexOfLastResult = currentPage * plansPerPage;
    const indexOfFirstResult = indexOfLastResult - plansPerPage;
    const currentResults = results.slice(indexOfFirstResult, indexOfLastResult);

    return (
        <>
            <NavigationBar />
            <ResultsList results={currentResults} />
            <Paginator
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={setCurrentPage}
            />
        </>
    )
}

export default TrainingProgress;