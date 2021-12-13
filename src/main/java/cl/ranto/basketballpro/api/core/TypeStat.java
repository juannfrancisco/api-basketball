package cl.ranto.basketballpro.api.core;

public enum TypeStat {

    BLK {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setBLK(  gameStatPlayer.getBLK() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setBLK( teamStat.getBLK() + value );
        }
    }, //Bloqueo

    OR {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setOR(  gameStatPlayer.getOR() + value  );
            gameStatPlayer.setREB(  gameStatPlayer.getREB() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setOR( teamStat.getOR() + value );
            teamStat.setREB( teamStat.getREB() + value );
        }
    }, //Rebote Ofensivo
    DR {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setDR(  gameStatPlayer.getDR() + value  );
            gameStatPlayer.setREB(  gameStatPlayer.getREB() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setDR( teamStat.getDR() + value );
            teamStat.setREB( teamStat.getREB() + value );
        }
    }, //Rebote Defensivo

    AST {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setAST(  gameStatPlayer.getAST() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setAST( teamStat.getAST() + value );
        }
    }, //Asistencia
    MAS {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setMAS(  gameStatPlayer.getMAS() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setMAS( teamStat.getMAS() + value );
        }
    }, //Missing Asistencia

    STL {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setSTL(  gameStatPlayer.getSTL() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setSTL( teamStat.getSTL() + value );
        }
    }, //Robo

    PTS {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setPTS(  gameStatPlayer.getPTS() + value  );
            if( value == 1 ){
                gameStatPlayer.setPTS1(gameStatPlayer.getPTS1() + value );
            }else if(value == 2) {
                gameStatPlayer.setPTS2(gameStatPlayer.getPTS2() + value);
            }else if( value == 3 ){
                gameStatPlayer.setPTS3(gameStatPlayer.getPTS3() + value);
            }
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setPTS( teamStat.getPTS() + value );
            if( value == 1 ){
                teamStat.setPTS1(teamStat.getPTS1() + value );
            }else if(value == 2) {
                teamStat.setPTS2(teamStat.getPTS2() + value);
            }else if( value == 3 ){
                teamStat.setPTS3(teamStat.getPTS3() + value);
            }
        }
    }, //Point
    PTS1 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setPTS1(  gameStatPlayer.getPTS1() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setPTS1( teamStat.getPTS1() + value );
        }
    }, //Point
    PTS2 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setPTS2(  gameStatPlayer.getPTS2() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setPTS2( teamStat.getPTS2() + value );
        }
    }, //Point
    PTS3 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setPTS3(  gameStatPlayer.getPTS3() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setPTS3( teamStat.getPTS3() + value );
        }
    }, //Point

    MPT {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setMPT(  gameStatPlayer.getMPT() + value  );
            if( value == 1 ){
                gameStatPlayer.setMPT1(gameStatPlayer.getMPT1() + value);
            }else if(value == 2) {
                gameStatPlayer.setMPT2(gameStatPlayer.getMPT2() + value);
            }else if( value == 3 ){
                gameStatPlayer.setMPT3(gameStatPlayer.getMPT3() + value);
            }
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setMPT( teamStat.getMPT() + value );
            if( value == 1 ){
                teamStat.setMPT1(teamStat.getMPT1() + value);
            }else if(value == 2) {
                teamStat.setMPT2(teamStat.getMPT2() + value);
            }else if( value == 3 ){
                teamStat.setMPT3(teamStat.getMPT3() + value);
            }
        }
    }, //Missing Point
    MPT1 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setMPT1(  gameStatPlayer.getMPT1() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setMPT1( teamStat.getMPT1() + value );
        }
    }, //Missing Point
    MPT2 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setMPT2(  gameStatPlayer.getMPT2() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setMPT2( teamStat.getMPT2() + value );
        }
    }, //Missing Point
    MPT3 {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setMPT3(  gameStatPlayer.getMPT3() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setMPT3( teamStat.getMPT3() + value );
        }
    }, //Missing Point

    PF {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
            gameStatPlayer.setPF(  gameStatPlayer.getPF() + value  );
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
            teamStat.setPF( teamStat.getPF() + value );
        }
    },
    REB {
        @Override
        public void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value) {
        }

        @Override
        public void addTeamStat(TeamStat teamStat, Integer value) {
        }
    };  //rebound


    public abstract void addGameStatPlayer(GameStatPlayer gameStatPlayer, Integer value);
    public abstract void addTeamStat(TeamStat teamStat, Integer value);




    }
