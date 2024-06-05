import {Box, Container} from "@mui/material";
import styles from "./styles.module.scss";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {useGetProfileByIdQuery} from "../../../../store/services/profileService.js";
import {fetchFundraisings} from '../../../../store/slices/Volunteer.slice.jsx'
import {useParams} from "react-router-dom";
import {ButtonMain} from "../../../../components/buttons/index.jsx";
import CreateFundraisingModal from "../../../../components/modals/CreateFundraisingModal/CreateFundraisingModal.jsx";
import FundraisingCard from "./FundraisingCard.jsx";
import DonateModal from "../../../../components/modals/DonateModal/DonateModal.jsx";
import FundraisingReportModal from "../../../../components/modals/FundraisingReportModal/FundraisingReportModal.jsx";


const Fundraisings = () => {
    const fundraisings = useSelector(state => state.volunteers.fundraisings)
    const [isFormOpen, setIsFormOpen] = useState(false);
    const [isDonate, setIsDonate] = useState(false);
    const [isReport, setIsReport] = useState(false);
    const [fundId, setFundId] = useState();

    const dispatch = useDispatch()
    const {id} = useParams();
    const {data: profile} = useGetProfileByIdQuery(id ?? localStorage.getItem('userId'));

    const isPersonalProfile = !id || id === localStorage.getItem('userId');

    useEffect(() => {
        dispatch(fetchFundraisings(profile.id))

        //eslint-disable-next-line
    }, [])


    return (
        <Box sx={{backgroundColor: (theme) => theme.palette.wash}}>
            <Container maxWidth={'lg'} sx={{p: 2}}>
                {isPersonalProfile && <ButtonMain onClick={() => setIsFormOpen(true)} fullWidth={false}>
                    Додати новий збір
                </ButtonMain>}
                <div className={styles.list}>
                    {fundraisings.map((f, i) => (
                        <div key={i}><FundraisingCard f={f} isPersonalProfile={isPersonalProfile}
                                                      onDonate={() => {
                                                          setIsDonate(true);
                                                          setFundId(f.id);
                                                      }} onReport={() => {
                            setIsReport(true);
                            setFundId(f.id);
                        }}/></div>
                    ))}
                </div>
            </Container>
            {isFormOpen && <CreateFundraisingModal open={isFormOpen} onClose={() => setIsFormOpen(false)}/>}
            {isDonate && <DonateModal fundId={fundId} open={isDonate} onClose={() => setIsDonate(false)}/>}
            {isReport && <FundraisingReportModal id={fundId} open={isReport} onClose={() => setIsReport(false)}/>}
        </Box>
    );
};

export default Fundraisings;
