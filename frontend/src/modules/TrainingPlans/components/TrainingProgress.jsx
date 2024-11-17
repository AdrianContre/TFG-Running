import { useState } from "react";
import { useEffect } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { getPlanResults } from "../services/trainingResultService";
import ResultsList from "./ResultsList";

function TrainingProgress() {
    const location = useLocation()
    const {planId} = location.state
    const [results, setResults] = useState([])
    useEffect(() => {
        const fetchInfo = async () => {
            const results = await getPlanResults(planId)
            console.log(results)
            setResults(results)
        }
        fetchInfo()
    },[])
    return (
        <>
            <NavigationBar />
            <ResultsList results={results} />
        </>
    )
}

export default TrainingProgress;