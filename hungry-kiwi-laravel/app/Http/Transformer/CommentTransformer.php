<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/15/2016
 * Time: 12:21 AM
 */
namespace App\Trans;

use App\Restr;
use App\User;

class CommentTransformer extends BasicTransformer
{
    public function transform($data)
    {
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
            'post_id' => $data['post_id'],
            'comment' => $data['comment'],
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