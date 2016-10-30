<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/26/2016
 * Time: 11:36 PM
 */

namespace App\Trans;


use App\Restr;
use App\User;

class RateCommentTransformer extends BasicTransformer{
    public function transform($data) {
        $userD = User::find($data['user_id']);
        $user = [
            'user' => [
                'id' => $data['user_id'],
                'first_name' => $userD->first_name,
                'last_name' => $userD->last_name,
                'is_restr_' => $userD->is_restr,
                'img' => $userD->img,
            ],
            'id' => $data['id'],
            'recipe_id' => $data['recipe_id'],
            'comment' => $data['comment'],
            'rate' => $data['rate'],
            'created_at' => $data['created_at']->toDateTimeString(),
        ];
        $restr = null;
        if ($userD->is_restr == 1) {
            $restrD = Restr::find($userD->id);
            return array_add($user, 'restr', [
                'restr_id' => $restrD->id,
                'img' => $restrD->img,
            ]);
        } else {
            return $user;
        }
    }


}