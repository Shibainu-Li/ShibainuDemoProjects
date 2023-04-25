//
// Created by Trust on 2023/4/25.
//

#include "SL_Player.h"


void SL_Player::preper() {
    if(file_path == nullptr){
        mBridge->callPlayerStatus(-1);
        return;
    }

}
