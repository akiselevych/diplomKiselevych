import PropTypes from "prop-types";
import {Stack, Avatar, Typography} from "@mui/material";

import {SearchItemWrapper} from "./MainSearchItem.styled";
import {userAvatar} from "../../../data/placeholders";
import styles from './styles.module.scss'
import diaIcon from './icons/diaIcon.svg'
import volunteerIcon from './icons/volunteer.svg'


const MainSearchItem = ({fullName, avatars, onClick, isVerified, isVolunteer}) => {
    return (
        <SearchItemWrapper onClick={onClick}>
            <div className={styles.avatar}>
                <Avatar
                    src={userAvatar({
                        avatarsUrl: avatars,
                        firstName: fullName.split(" ")[0],
                        lastName: fullName.split(" ")[1],
                    })}
                    alt={fullName}
                    sx={{width: 45, height: 45}}
                />
                {isVerified && <img src={diaIcon} alt="diaIcon" className={styles.diaIcon}/>}
                {isVolunteer && <img src={volunteerIcon} alt="diaIcon" className={styles.volunteerIcon}/>}
            </div>


            <Stack direction="row" width="100%" justifyContent="space-between">
                <Typography>{fullName}</Typography>
            </Stack>
        </SearchItemWrapper>
    );
};

MainSearchItem.propTypes = {
    fullName: PropTypes.string,
    avatars: PropTypes.array,
    onClick: PropTypes.func,
    isVolunteer: PropTypes.bool,
    isVerified: PropTypes.bool
};

MainSearchItem.displayName = "MainSearchItem";

export default MainSearchItem;
