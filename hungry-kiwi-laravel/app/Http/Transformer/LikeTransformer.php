<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/15/2016
 * Time: 8:58 PM
 */

namespace App\Trans;


use App\Restr;
use App\User;

class LikeTransformer extends BasicTransformer {
    public function transform($data)
    {
        $user_id = $data['user_id'];
        $user = User::find($user_id);
        $result = [
            'user' => [
            'user_id' => $user -> id,
            'first_name' => $user ->first_name,
            'last_name' => $user ->last_name,
            'is_restr' => $user -> is_restr,
            'img' => $user -> img,
            ]
        ];

        if($user -> is_restr == 1) {
            $restr = Restr::where('user_id', $user -> id) -> first();
            $result = array_add($result, 'restr', [
                    'name' => $restr -> name,
                    'img' => $restr -> img,
                ]);
        }

        return $result;
    }


}